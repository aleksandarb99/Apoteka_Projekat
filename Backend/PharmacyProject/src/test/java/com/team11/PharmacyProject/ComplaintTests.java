package com.team11.PharmacyProject;

import com.team11.PharmacyProject.complaint.Complaint;
import com.team11.PharmacyProject.complaint.ComplaintRepository;
import com.team11.PharmacyProject.complaint.ComplaintService;
import com.team11.PharmacyProject.complaintResponse.ComplaintResponseService;
import com.team11.PharmacyProject.dto.complaintResponse.ComplaintResponseDTO;
import com.team11.PharmacyProject.exceptions.CustomException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.OptimisticLockException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintTests {

    @Autowired
    private ComplaintResponseService complaintResponseService;

    @Test
    public void multipleAdminsSubmittedComplaint() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable r1 = () -> {
            ComplaintResponseDTO response = new ComplaintResponseDTO();
            response.setComplaintId(3L);
            response.setAdminId(8);
            response.setResponseText("AAAAA");
            System.out.println("Prvi admin odgovara");
            try {
                complaintResponseService.submitResponse(response);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
            System.out.println("Prvi admin odgovorio");

        };

        Runnable r2 = () -> {
            ComplaintResponseDTO response = new ComplaintResponseDTO();
            response.setComplaintId(3L);
            response.setAdminId(19);
            response.setResponseText("BBBBB");
            System.out.println("Drugi admin odgovara");
            try {
                complaintResponseService.submitResponse(response);
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
            System.out.println("Drugi admin odgovario");

        };

        Future<?> future = executor.submit(r1);
        executor.submit(r2);

        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Exception");
        }
    }

}
