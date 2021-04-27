package com.team11.PharmacyProject.myOrder;


import com.team11.PharmacyProject.dto.order.MyOrderDTO;
import com.team11.PharmacyProject.dto.order.MyOrderAddingDTO;

import java.util.List;

public interface MyOrderService {

    List<MyOrder> getOrdersByPharmacyId(Long id, String filterValue);
    List<MyOrderDTO> getAvailableOrders();
    boolean addOrder(MyOrderAddingDTO dto);

    MyOrderDTO getOrder(long id);
}
