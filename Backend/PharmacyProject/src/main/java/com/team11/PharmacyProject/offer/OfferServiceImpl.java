package com.team11.PharmacyProject.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    OfferRepository offerRepository;

    @Override
    public void save(Offer o) {
        offerRepository.save(o);
    }

    @Override
    public List<Offer> findOffersByOrderId(long orderId) {
        return offerRepository.findOffersByOrderId(orderId);
    }
}
