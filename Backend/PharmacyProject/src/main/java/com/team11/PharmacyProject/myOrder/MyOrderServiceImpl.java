package com.team11.PharmacyProject.myOrder;


import com.team11.PharmacyProject.dto.order.MyOrderDTO;
import com.team11.PharmacyProject.enums.OrderState;
import com.team11.PharmacyProject.offer.Offer;
import com.team11.PharmacyProject.offer.OfferService;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import com.team11.PharmacyProject.users.supplier.Supplier;
import com.team11.PharmacyProject.users.supplier.SupplierRepository;
import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.users.user.UserService;
import org.modelmapper.ModelMapper;
import com.team11.PharmacyProject.dto.order.MyOrderAddingDTO;
import com.team11.PharmacyProject.dto.order.OrderItemAddingDTO;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import com.team11.PharmacyProject.orderItem.OrderItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    private MyOrderRepository myOrderRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private UserService userService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    OfferService offerService;

    @Override
    public List<MyOrder> getOrdersByPharmacyId(Long id, String filterValue) {
        List<MyOrder> myOrderList = new ArrayList<>();
        long currentTime = new Date().getTime();

        for (MyOrder order : myOrderRepository.getOrdersByPharmacyId(id)) {
            if (filterValue.equals("All")) {
                myOrderList.add(order);
            } else if (filterValue.equals("InProgress") && order.getOrderState().equals(OrderState.IN_PROGRESS)) {
                myOrderList.add(order);
            } else if (filterValue.equals("Processed") && order.getOrderState().equals(OrderState.ENDED)) {
                myOrderList.add(order);
            } else if (filterValue.equals("OnHold") && order.getOrderState().equals(OrderState.ON_HOLD)) {
                myOrderList.add(order);
            }
        }
        return myOrderList;
    }

    public List<MyOrderDTO> getAvailableOrders() {
        List<MyOrder> orders = myOrderRepository.getAllByDeadlineAfter(System.currentTimeMillis());
        return orders.stream()
                .map(myOrder -> modelMapper.map(myOrder, MyOrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeOrder(long orderId) {
        Optional<MyOrder> order = myOrderRepository.findById(orderId);
        if(order.isEmpty())
            return false;
        List<Offer> offers = offerService.findOffersByOrderId(orderId);
        if(offers.isEmpty()) {
            myOrderRepository.delete(order.get());
            return true;
        }
        return false;

    }

    @Override
    public boolean editOrder(long orderId, long date) {
        Optional<MyOrder> order = myOrderRepository.findById(orderId);
        if(order.isEmpty())
            return false;

        Date now = new Date();
        if(now.getTime() > date)
            return false;

        List<Offer> offers = offerService.findOffersByOrderId(orderId);
        if(!offers.isEmpty()) {
            return false;
        }

        MyOrder order1 = order.get();
        order1.setDeadline(date);
        myOrderRepository.save(order1);
        return true;
    }

    public boolean addOrder(MyOrderAddingDTO dto) {
        Pharmacy pharmacy = pharmacyService.getPharmacyById(dto.getPharmacyId());
        if(pharmacy==null){
            return false;
        }
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemAddingDTO item:dto.getItems()) {
            Medicine m = medicineService.getMedicineById(item.getMedicineId());
            if(m==null){
                return false;
            }
            if(item.getAmount()<1){
                return false;
            }
            OrderItem order = new OrderItem(item.getAmount(), m);
            items.add(order);
        }
        if(items.size() < 1){
            return false;
        }

        Date now = new Date();
        if(dto.getDeadline() <= now.getTime()){
            return false;
        }

        MyUser admin = userService.findOne(dto.getAdminId());

        MyOrder order = new MyOrder(dto.getDeadline(), pharmacy, items, admin);
        myOrderRepository.save(order);
        return true;
    }

    @Override
    public Long getAdminIdOfOrderId(Long id) {
        MyOrder order = myOrderRepository.findOrderByIdWithAdmin(id);
        return order.getAdmin().getId();
    }

    @Override
    public MyOrderDTO getOrder(long id) {
        Optional<MyOrder> myOrder = myOrderRepository.getMyOrderById(id);
        if (myOrder.isEmpty()) return null;
        return modelMapper.map(myOrder.get(), MyOrderDTO.class);
    }

    @Override
    public List<MyOrderDTO> getAvailableOrdersForSupplier(long supplierId) {
        Optional<Supplier> s = supplierRepository.findSupplierWithOffersUsingId(supplierId);
        if (s.isEmpty()) {
            return new ArrayList<>();
        }
        Supplier supp = s.get();

        List<MyOrder> orders = myOrderRepository.getAllByDeadlineAfter(System.currentTimeMillis());
        List<MyOrder> ordersForSupplier = orders.stream().filter(myOrder -> !isInOffer(myOrder, supp.getOffers())).collect(Collectors.toList());
        return ordersForSupplier.stream()
                .map(myOrder -> modelMapper.map(myOrder, MyOrderDTO.class))
                .collect(Collectors.toList());
    }

    private boolean isInOffer(MyOrder order, List<Offer> offers) {
        return offers.stream().anyMatch(offer -> offer.getOrder().getId().equals(order.getId()));
    }
}
