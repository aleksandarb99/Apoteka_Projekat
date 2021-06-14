package com.team11.PharmacyProject.myOrder;


import com.team11.PharmacyProject.dto.order.MyOrderAddingDTO;
import com.team11.PharmacyProject.dto.order.MyOrderDTO;

import java.util.List;

public interface MyOrderService {

    List<MyOrder> getOrdersByPharmacyId(Long id, String filterValue);

    List<MyOrderDTO> getAvailableOrders();

    void addOrder(MyOrderAddingDTO dto);

    MyOrderDTO getOrder(long id);

    List<MyOrderDTO> getAvailableOrdersForSupplier(long supplierId);

    Long getAdminIdOfOrderId(Long id);

    void removeOrder(long orderId);

    void editOrder(long orderId, long date);

    void checkIfOrderIsOver();
}
