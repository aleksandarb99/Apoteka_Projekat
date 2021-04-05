package com.team11.PharmacyProject.myOrder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    private MyOrderRepository myOrderRepository;

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
}
