package com.team11.PharmacyProject;

import com.team11.PharmacyProject.dto.complaintResponse.ComplaintResponseDTO;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDispenseDTO;
import com.team11.PharmacyProject.eRecipe.ERecipeService;
import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;
import com.team11.PharmacyProject.enums.ERecipeState;
import com.team11.PharmacyProject.exceptions.CustomException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ERecipeTest {

    @Autowired
    private ERecipeService eRecipeService;

    @Test(expected = PessimisticLockingFailureException.class)
    public void dispenseMedicineTest() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable r1 = () -> {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Izdaje se lek prvog pacijenta");
                ERecipeDispenseDTO eRecipeDispenseDTO = new ERecipeDispenseDTO();
                eRecipeDispenseDTO.setPrescriptionDate(1621062749696L);
                eRecipeDispenseDTO.setState(ERecipeState.NEW);
                eRecipeDispenseDTO.setCode("EP0003");
                eRecipeDispenseDTO.setPatientId(1L);
                eRecipeDispenseDTO.setPharmacyId(3L);
                eRecipeDispenseDTO.setTotalPrice(16345.0);
                List<ERecipeItem> items = new ArrayList<>();
                ERecipeItem i1 = new ERecipeItem();
                i1.setMedicineCode("M01AE01");
                i1.setMedicineName("Brufen");
                i1.setQuantity(35);
                items.add(i1);
                eRecipeDispenseDTO.seteRecipeItems(items);
                eRecipeService.dispenseMedicine(1, eRecipeDispenseDTO);
                System.out.println("Izdat lek prvog pacijenta");
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        };

        Runnable r2 = () -> {
            System.out.println("Izdaje se lek drugog pacijenta");

            try {
                ERecipeDispenseDTO eRecipeDispenseDTO = new ERecipeDispenseDTO();
                eRecipeDispenseDTO.setPrescriptionDate(1621062941120L);
                eRecipeDispenseDTO.setState(ERecipeState.NEW);
                eRecipeDispenseDTO.setCode("EP0005");
                eRecipeDispenseDTO.setPatientId(3L);
                eRecipeDispenseDTO.setPharmacyId(3L);
                eRecipeDispenseDTO.setTotalPrice(3080.0);
                List<ERecipeItem> items = new ArrayList<>();
                ERecipeItem i1 = new ERecipeItem();
                i1.setMedicineCode("M01AE01");
                i1.setMedicineName("Brufen");
                i1.setQuantity(6);
                ERecipeItem i2 = new ERecipeItem();
                i2.setMedicineCode("N02BE01");
                i2.setMedicineName("Paracetamol");
                i2.setQuantity(1);
                items.add(i1);
                items.add(i2);
                eRecipeDispenseDTO.seteRecipeItems(items);
                eRecipeService.dispenseMedicine(3, eRecipeDispenseDTO);
                System.out.println("Izdat lek drugog pacijenta");
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        };

        executor.submit(r2);
        Future<?> future = executor.submit(r1);

        try {
            future.get();
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass());
            throw e.getCause();
        }
    }

    @Test
    public void dispenseDifferentMedicineTest() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable r1 = () -> {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Izdaje se lek prvog pacijenta");
                ERecipeDispenseDTO eRecipeDispenseDTO = new ERecipeDispenseDTO();
                eRecipeDispenseDTO.setPrescriptionDate(1621062749696L);
                eRecipeDispenseDTO.setState(ERecipeState.NEW);
                eRecipeDispenseDTO.setCode("EP0003");
                eRecipeDispenseDTO.setPatientId(1L);
                eRecipeDispenseDTO.setPharmacyId(3L);
                eRecipeDispenseDTO.setTotalPrice(16345.0);
                List<ERecipeItem> items = new ArrayList<>();
                ERecipeItem i1 = new ERecipeItem();
                i1.setMedicineCode("M01AE01");
                i1.setMedicineName("Brufen");
                i1.setQuantity(35);
                items.add(i1);
                eRecipeDispenseDTO.seteRecipeItems(items);
                eRecipeService.dispenseMedicine(1, eRecipeDispenseDTO);
                System.out.println("Izdat lek prvog pacijenta");
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        };

        Runnable r2 = () -> {
            System.out.println("Izdaje se lek drugog pacijenta");

            try {
                ERecipeDispenseDTO eRecipeDispenseDTO = new ERecipeDispenseDTO();
                eRecipeDispenseDTO.setPrescriptionDate(1621062941120L);
                eRecipeDispenseDTO.setState(ERecipeState.NEW);
                eRecipeDispenseDTO.setCode("EP0005");
                eRecipeDispenseDTO.setPatientId(3L);
                eRecipeDispenseDTO.setPharmacyId(3L);
                eRecipeDispenseDTO.setTotalPrice(3080.0);
                List<ERecipeItem> items = new ArrayList<>();
                ERecipeItem i2 = new ERecipeItem();
                i2.setMedicineCode("N02BE01");
                i2.setMedicineName("Paracetamol");
                i2.setQuantity(1);
                items.add(i2);
                eRecipeDispenseDTO.seteRecipeItems(items);
                eRecipeService.dispenseMedicine(3, eRecipeDispenseDTO);
                System.out.println("Izdat lek drugog pacijenta");
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
        };

        executor.submit(r2);
        Future<?> future = executor.submit(r1);

        try {
            future.get();
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass());
            throw e.getCause();
        }
    }
}
