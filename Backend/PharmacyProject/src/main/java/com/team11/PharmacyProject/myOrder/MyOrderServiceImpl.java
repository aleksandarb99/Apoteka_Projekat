package com.team11.PharmacyProject.myOrder;


import com.team11.PharmacyProject.dto.order.MyOrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    private MyOrderRepository myOrderRepository;
    @Autowired
    private ModelMapper modelMapper;

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

    @Override
    public List<MyOrderDTO> getAvailableOrders() {
        List<MyOrder> orders = myOrderRepository.getAllByDeadlineAfter(System.currentTimeMillis());
        return orders.stream()
                .map(myOrder -> modelMapper.map(myOrder, MyOrderDTO.class))
                .collect(Collectors.toList());
    }
}
