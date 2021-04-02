package com.team11.PharmacyProject.myOrder;


import java.util.List;

public interface MyOrderService {

    List<MyOrder> getOrdersByPharmacyId(Long id);
}
