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
    public void multipleAdminsSubmittedComplaint() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable r1 = () -> {
            ComplaintResponseDTO response = new ComplaintResponseDTO();
            response.setComplaintId(3L);
            response.setAdminId(8);
            response.setResponseText("AAAAA");
            try {
                System.out.println("Prvi admin odgovara");
                complaintResponseService.submitResponse(response);
                System.out.println("Prvi admin odgovorio");
            } catch (CustomException customException) {
                System.out.println(customException.getMessage());
            }

        };

        Runnable r2 = () -> {
            ComplaintResponseDTO response = new ComplaintResponseDTO();
            response.setComplaintId(3L);
            response.setAdminId(19);
            response.setResponseText("BBBBB");
            try {
                System.out.println("Drugi admin odgovara");
                complaintResponseService.submitResponse(response);
                System.out.println("Drugi admin odgovario");
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
