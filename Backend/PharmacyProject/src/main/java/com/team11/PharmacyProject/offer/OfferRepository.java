package com.team11.PharmacyProject.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("SELECT o FROM Offer o WHERE o.order.id = ?1")
    List<Offer> findOffersByOrderId(long orderId);

    @Query("SELECT o FROM Offer o WHERE (o.offerState='ACCEPTED' and o.deliveryDate > ?1 and o.deliveryDate < ?2 and o.order.pharmacy.id = ?3)")
    List<Offer> getOffersBeetwenTwoTimestamps(long start, long end, long pharmacyId);
}
