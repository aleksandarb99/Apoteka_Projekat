package com.team11.PharmacyProject.myOrder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    private MyOrderRepository myOrderRepository;

    @Override
    public List<MyOrder> getOrdersByPharmacyId(Long id) {
        List<MyOrder> myOrderList = new ArrayList<>();
        myOrderRepository.getOrdersByPharmacyId(id).forEach(myOrderList::add);
        return myOrderList;
    }
}
