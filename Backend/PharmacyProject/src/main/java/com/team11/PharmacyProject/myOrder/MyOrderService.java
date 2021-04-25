package com.team11.PharmacyProject.myOrder;


import com.team11.PharmacyProject.dto.order.MyOrderAddingDTO;

import java.util.List;

public interface MyOrderService {

    List<MyOrder> getOrdersByPharmacyId(Long id, String filterValue);

    boolean addOrder(MyOrderAddingDTO dto);
}
