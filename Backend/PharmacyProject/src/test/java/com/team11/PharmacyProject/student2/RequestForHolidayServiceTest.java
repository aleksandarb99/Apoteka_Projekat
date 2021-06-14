package com.team11.PharmacyProject.student2;

import com.team11.PharmacyProject.appointment.AppointmentRepository;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.enums.AbsenceRequestState;
import com.team11.PharmacyProject.requestForHoliday.RequestForHoliday;
import com.team11.PharmacyProject.requestForHoliday.RequestForHolidayRepository;
import com.team11.PharmacyProject.requestForHoliday.RequestForHolidayServiceImpl;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import com.team11.PharmacyProject.workplace.WorkplaceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestForHolidayServiceTest {

    @Mock
    RequestForHolidayRepository requestForHolidayRepositoryMock;

    @Mock
    AppointmentRepository appointmentRepositoryMock;

    @Mock
    PharmacyWorkerRepository pharmacyWorkerRepositoryMock;

    @Mock
    WorkplaceService workplaceServiceMock;

    @Mock
    EmailService emailServiceMock;

    @InjectMocks
    RequestForHolidayServiceImpl requestForHolidayService;

    @Test()
    @Transactional
    @Rollback(value = true)
    public void acceptRequest() {

        RequestForHoliday r1 = new RequestForHoliday();
        r1.setId(1L);
        r1.setRequestState(AbsenceRequestState.PENDING);

        Mockito.when(requestForHolidayRepositoryMock.findOneWithWorker(1L)).thenReturn(r1);
        Mockito.when(requestForHolidayRepositoryMock.save(r1)).thenReturn(r1);

        RequestForHoliday r2 = requestForHolidayService.acceptRequest("worker 1");
        Assertions.assertEquals(r2.getRequestState(), AbsenceRequestState.ACCEPTED);

        Mockito.verify(requestForHolidayRepositoryMock, Mockito.times(1)).save(r1);
    }

}
