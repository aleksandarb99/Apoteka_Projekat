package com.team11.PharmacyProject.offer;

import com.team11.PharmacyProject.appointment.Appointment;
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

    @Override
    public double calculateExpenses(long start, long end, long pharmacyId) {
        double sum = 0;
        List<Offer> list = offerRepository.getOffersBeetwenTwoTimestamps(start, end, pharmacyId);
        for (Offer a:list) {
            sum += a.getPrice();
        }
        return sum;
    }
}
