package com.team11.PharmacyProject.myOrder;


import com.team11.PharmacyProject.dto.order.MyOrderDTO;
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

    @Override
    public List<MyOrder> getOrdersByPharmacyId(Long id, String filterValue) {
        List<MyOrder> myOrderList = new ArrayList<>();
        long currentTime = new Date().getTime();

        for (MyOrder order : myOrderRepository.getOrdersByPharmacyId(id)) {
            if (filterValue.equals("All")) {
                myOrderList.add(order);
            } else if (filterValue.equals("InProgress") && currentTime < order.getDeadline()) {
                myOrderList.add(order);
            } else if (filterValue.equals("Processed") && currentTime > order.getDeadline()) {
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

        MyOrder order = new MyOrder(dto.getDeadline(), pharmacy, items);
        myOrderRepository.save(order);
        return true;
    }

    @Override
    public MyOrderDTO getOrder(long id) {
        Optional<MyOrder> myOrder = myOrderRepository.getMyOrderById(id);
        if (myOrder.isEmpty()) return null;
        return modelMapper.map(myOrder.get(), MyOrderDTO.class);
    }
}
