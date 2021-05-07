package com.team11.PharmacyProject.offer;

import java.util.List;

public interface OfferService {

    void save(Offer o);

    List<Offer> findOffersByOrderId(long orderId);
}
