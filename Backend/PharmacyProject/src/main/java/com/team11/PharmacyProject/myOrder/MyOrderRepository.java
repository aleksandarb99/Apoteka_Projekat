package com.team11.PharmacyProject.myOrder;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyOrderRepository extends CrudRepository<MyOrder, Long> {

    @Query("SELECT u FROM MyOrder u WHERE u.pharmacy.id = ?1")
    Iterable<MyOrder> getOrdersByPharmacyId(Long id);
}
