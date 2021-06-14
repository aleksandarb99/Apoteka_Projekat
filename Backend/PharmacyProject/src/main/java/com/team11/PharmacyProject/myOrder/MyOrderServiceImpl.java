package com.team11.PharmacyProject.myOrder;


import com.team11.PharmacyProject.dto.order.MyOrderAddingDTO;
import com.team11.PharmacyProject.dto.order.MyOrderDTO;
import com.team11.PharmacyProject.dto.order.OrderItemAddingDTO;
import com.team11.PharmacyProject.enums.OrderState;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import com.team11.PharmacyProject.offer.Offer;
import com.team11.PharmacyProject.offer.OfferService;
import com.team11.PharmacyProject.orderItem.OrderItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import com.team11.PharmacyProject.users.supplier.Supplier;
import com.team11.PharmacyProject.users.supplier.SupplierRepository;
import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.users.user.UserService;
import org.modelmapper.ModelMapper;
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
    OfferService offerService;
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
    public void removeOrder(long orderId) {
        Optional<MyOrder> order = myOrderRepository.findById(orderId);
        if (order.isEmpty())
            throw new RuntimeException("Order with id " + orderId + " does not exist!");
        List<Offer> offers = offerService.findOffersByOrderId(orderId);
        if (offers.isEmpty()) {
            myOrderRepository.delete(order.get());
        } else {
            throw new RuntimeException("Order already have offers, and cannot be deleted!");
        }
    }

    @Override
    public void editOrder(long orderId, long date) {
        Optional<MyOrder> order = myOrderRepository.findById(orderId);
        if (order.isEmpty())
            throw new RuntimeException("Order with id " + orderId + " does not exist!");

        Date now = new Date();
        if (now.getTime() > date)
            throw new RuntimeException("New deadline must be in the future!");

        List<Offer> offers = offerService.findOffersByOrderId(orderId);
        if (!offers.isEmpty()) {
            throw new RuntimeException("Order already has offers, and cannot be edited!");
        }

        MyOrder order1 = order.get();
        order1.setDeadline(date);
        myOrderRepository.save(order1);
    }

    @Override
    public void checkIfOrderIsOver() {
        Iterable<MyOrder> all = myOrderRepository.findAll();
        for (MyOrder order : all) {
            if (System.currentTimeMillis() > order.getDeadline() && order.getOrderState().equals(OrderState.IN_PROGRESS)) {
                order.setOrderState(OrderState.ON_HOLD);
                myOrderRepository.save(order);
            }
        }
    }

    public void addOrder(MyOrderAddingDTO dto) {
        Pharmacy pharmacy = pharmacyService.getPharmacyById(dto.getPharmacyId());
        if (pharmacy == null) {
            throw new RuntimeException("Pharmacy with id " + dto.getPharmacyId() + " does not exist!");
        }
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemAddingDTO item : dto.getItems()) {
            Medicine m = medicineService.getMedicineById(item.getMedicineId());
            if (m == null) {
                throw new RuntimeException("Medicine with id " + item.getMedicineId() + " does not exist!");
            }
            if (item.getAmount() < 1) {
                throw new RuntimeException("Amount must be greater than 0!");
            }
            OrderItem order = new OrderItem(item.getAmount(), m);
            items.add(order);
        }
        if (items.size() < 1) {
            throw new RuntimeException("Order must have order items! Cannot be empty!");
        }

        Date now = new Date();
        if (dto.getDeadline() <= now.getTime()) {
            throw new RuntimeException("Deadline must be in the future!");
        }

        MyUser admin = userService.findOne(dto.getAdminId());

        MyOrder order = new MyOrder(dto.getDeadline(), pharmacy, items, admin);
        myOrderRepository.save(order);
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
