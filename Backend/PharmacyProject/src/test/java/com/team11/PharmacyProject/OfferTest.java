package com.team11.PharmacyProject;

import com.team11.PharmacyProject.complaintResponse.ComplaintResponseService;
import com.team11.PharmacyProject.dto.complaintResponse.ComplaintResponseDTO;
import com.team11.PharmacyProject.dto.offer.OfferListDTO;
import com.team11.PharmacyProject.enums.OfferState;
import com.team11.PharmacyProject.exceptions.CustomException;
import com.team11.PharmacyProject.users.supplier.SupplierService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferTest {

    @Autowired
    private SupplierService supplierService;

    @Test(expected = PessimisticLockingFailureException.class)
    public void multipleOffers() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable r1 = () -> {
            try {
                System.out.println("Pravi prvu ponudu");
                OfferListDTO offerListDTO = new OfferListDTO();
                offerListDTO.setPrice(1000);
                offerListDTO.setDeliveryDate(1623783853000L);
                offerListDTO.setOfferState(OfferState.PENDING);
                offerListDTO.setOrderId(8);
                supplierService.insertOffer(5, offerListDTO);
                System.out.println("Napravio prvu ponudu");
            } catch (CustomException customException) {
                System.out.println(customException.getMessage());
            }

        };

        Runnable r2 = () -> {
            try {
                System.out.println("Pravi drugu ponudu");
                OfferListDTO offerListDTO = new OfferListDTO();
                offerListDTO.setPrice(1200);
                offerListDTO.setDeliveryDate(1623783853000L);
                offerListDTO.setOfferState(OfferState.PENDING);
                offerListDTO.setOrderId(9);
                supplierService.insertOffer(5, offerListDTO);
                System.out.println("Napravio drugu ponudu");
            } catch (CustomException customException) {
                System.out.println(customException.getMessage());
            }

        };

        executor.submit(r2);
        Future<?> future = executor.submit(r1);

        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Exception from thread " + e.getCause().getClass());
            throw e.getCause();
        }
    }

}
