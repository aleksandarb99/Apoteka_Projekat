package com.team11.PharmacyProject.myOrder;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyOrderRepository extends CrudRepository<MyOrder, Long> {

    @Query("SELECT u FROM MyOrder u WHERE u.pharmacy.id = ?1")
    Iterable<MyOrder> getOrdersByPharmacyId(Long id);

    List<MyOrder> getAllByDeadlineAfter(Long deadline);

    @Query("SELECT u FROM MyOrder u JOIN FETCH u.pharmacy p JOIN FETCH p.priceList pl JOIN FETCH pl.medicineItems mi WHERE u.id = ?1")
    Optional<MyOrder> getMyOrderById(long id);

    @Query("SELECT u FROM MyOrder u LEFT JOIN FETCH u.admin WHERE u.id = ?1")
    MyOrder findOrderByIdWithAdmin(Long id);
}
