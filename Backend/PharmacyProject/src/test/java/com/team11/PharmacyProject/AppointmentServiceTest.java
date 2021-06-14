package com.team11.PharmacyProject;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.appointment.AppointmentRepository;
import com.team11.PharmacyProject.appointment.AppointmentService;
import com.team11.PharmacyProject.appointment.AppointmentServiceImpl;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.Weekday;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservationServiceImpl;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.rankingCategory.RankingCategory;
import com.team11.PharmacyProject.rankingCategory.RankingCategoryService;
import com.team11.PharmacyProject.rankingCategory.RankingCategoryServiceImpl;
import com.team11.PharmacyProject.requestForHoliday.RequestForHolidayService;
import com.team11.PharmacyProject.requestForHoliday.RequestForHolidayServiceImpl;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import com.team11.PharmacyProject.users.user.Role;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import com.team11.PharmacyProject.workplace.WorkplaceService;
import com.team11.PharmacyProject.workplace.WorkplaceServiseImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.querydsl.binding.OptionalValueBinding;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentServiceTest {

    @InjectMocks
    AppointmentServiceImpl appointmentServiceMock;

    @Mock
    WorkplaceServiseImpl workplaceServiceMock;

    @Mock
    RankingCategoryServiceImpl rankingCategoryServiceMock;

    @Mock
    RequestForHolidayServiceImpl requestForHolidayServiceMock;

    @Mock
    PatientRepository patientRepositoryMock;

    @Mock
    PharmacyRepository pharmacyRepositoryMock;

    @Mock
    PharmacyWorkerRepository pharmacyWorkerRepositoryMock;

    @Mock
    AppointmentRepository appointmentRepositoryMock;



    @Test()
    @Transactional
    @Rollback(value = true)
    public void scheduleAppointmentInRangeTest(){
        Long apptStart = 1646647200000L;
        Long apptEnd = 1646648200000L;

        PharmacyWorker worker = new PharmacyWorker();
        worker.setId(1L);
        Role role = new Role();
        role.setName("PHARMACIST");
        worker.setRole(role);

        Patient patient = new Patient();
        patient.setFirstName("Mika");
        patient.setLastName("Mikic");
        patient.setEmail("mikamikic@gmail.com");
        patient.setId(2L);
        patient.setPenalties(1);
        patient.setPoints(80);

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(3L);
        pharmacy.setName("Test apoteka");
        pharmacy.setAddress(null);
        pharmacy.setConsultationPrice(500d);

        RankingCategory rk = new RankingCategory();
        rk.setDiscount(10);
        rk.setPointsRequired(70);

        Appointment appointment1 = new Appointment();
        appointment1.setPatient(patient);
        appointment1.setWorker(worker);
        appointment1.setStartTime(1646645200000L);
        appointment1.setEndTime(1646646200000L);

        Appointment appointment2 = new Appointment();
        appointment2.setPatient(patient);
        appointment2.setWorker(worker);
        appointment2.setStartTime(1646615200000L);
        appointment2.setEndTime(1646615280000L);

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment1); appointmentList.add(appointment2);

        Workplace workplace = new Workplace();
        workplace.setWorkDays(List.of(new WorkDay(Weekday.MON, 10, 18),
                new WorkDay(Weekday.TUE, 9, 18)));

        Mockito.when(pharmacyWorkerRepositoryMock.findById(1L)).thenReturn(Optional.of(worker));
        Mockito.when(appointmentRepositoryMock.appointmentsOfWorkerAndPatient(1L, 2L)).thenReturn(appointmentList);
        Mockito.when(workplaceServiceMock.getWorkplaceOfPharmacist(1L)).thenReturn(workplace);
        Mockito.when(requestForHolidayServiceMock.hasVacationInThatDateRange(1L, apptStart, apptEnd)).thenReturn(false);
        Mockito.when(patientRepositoryMock.findById(2L)).thenReturn(Optional.of(patient));
        Mockito.when(pharmacyRepositoryMock.findById(3L)).thenReturn(Optional.of(pharmacy));
        Mockito.when(rankingCategoryServiceMock.getCategoryByPoints(patient.getPoints())).thenReturn(rk);

        try {
            Appointment appt = appointmentServiceMock.scheduleAppointmentInRange(1L, 2L, 3L, apptStart, apptEnd, 20);
            assertEquals(450, appt.getPrice(), 0);
            assertEquals(apptStart, appt.getStartTime());
            assertEquals(apptEnd, appt.getEndTime());
        }catch (Exception e){
            fail();
            System.out.println("exception while adding appointment");
        }
    }

    @Test()
    @Transactional
    @Rollback(value = true)
    public void scheduleAppointmentInRangeFailTest(){
        long apptStart = 1646647200000L;
        long apptEnd = 1646648200000L;

        PharmacyWorker worker = new PharmacyWorker();
        worker.setId(1L);
        Role role = new Role();
        role.setName("PHARMACIST");
        worker.setRole(role);

        Patient patient = new Patient();
        patient.setFirstName("Mika");
        patient.setLastName("Mikic");
        patient.setEmail("mikamikic@gmail.com");
        patient.setId(2L);
        patient.setPenalties(1);
        patient.setPoints(80);

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(3L);
        pharmacy.setName("Test apoteka");
        pharmacy.setAddress(null);
        pharmacy.setConsultationPrice(500d);

        RankingCategory rk = new RankingCategory();
        rk.setDiscount(10);
        rk.setPointsRequired(70);

        Appointment appointment1 = new Appointment();
        appointment1.setPatient(patient);
        appointment1.setWorker(worker);
        appointment1.setStartTime(1646647300000L);  //failuje jer se preklapa
        appointment1.setEndTime(1646649200000L);    //failuje jer se preklapa

        Appointment appointment2 = new Appointment();
        appointment2.setPatient(patient);
        appointment2.setWorker(worker);
        appointment2.setStartTime(1646615200000L);
        appointment2.setEndTime(1646615280000L);

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment1); appointmentList.add(appointment2);

        Workplace workplace = new Workplace();
        workplace.setWorkDays(List.of(new WorkDay(Weekday.MON, 10, 18),
                new WorkDay(Weekday.TUE, 9, 18)));

        Mockito.when(pharmacyWorkerRepositoryMock.findById(1L)).thenReturn(Optional.of(worker));
        Mockito.when(appointmentRepositoryMock.appointmentsOfWorkerAndPatient(1L, 2L)).thenReturn(appointmentList);
        Mockito.when(workplaceServiceMock.getWorkplaceOfPharmacist(1L)).thenReturn(workplace);
        Mockito.when(requestForHolidayServiceMock.hasVacationInThatDateRange(1L, apptStart, apptEnd)).thenReturn(false);
        Mockito.when(patientRepositoryMock.findById(2L)).thenReturn(Optional.of(patient));
        Mockito.when(pharmacyRepositoryMock.findById(3L)).thenReturn(Optional.of(pharmacy));
        Mockito.when(rankingCategoryServiceMock.getCategoryByPoints(patient.getPoints())).thenReturn(rk);

        try {
            Appointment appt = appointmentServiceMock.scheduleAppointmentInRange(1L, 2L, 3L, apptStart, apptEnd, 20);
            fail(); //mora da padne jer se preklapaju sastanci
        }catch (Exception e){
            System.out.println("exception thrown");
        }

        apptStart = 1646615100000L; //fail jer se preklapa sa drugim sastankom
        apptEnd = 1646615290000L;

        try {
            Appointment appt = appointmentServiceMock.scheduleAppointmentInRange(1L, 2L, 3L, apptStart, apptEnd, 20);
            fail(); //mora da padne jer se preklapaju sastanci
        }catch (Exception e){
            System.out.println("exception thrown");
        }

        apptStart = 1646640000000L; //fail jer se ne poklapa sa radnim vremenom (ponedeljak u 9)
        apptEnd = 1646640500000L;

        try {
            Appointment appt = appointmentServiceMock.scheduleAppointmentInRange(1L, 2L, 3L, apptStart, apptEnd, 20);
            fail(); //mora da padne jer nije u okviru radnog vremena
        }catch (Exception e){
            System.out.println("exception thrown");
        }
    }

    @Test()
    @Transactional
    @Rollback(value = true)
    public void startAppointmentTest() {
        long currTime = Instant.now().toEpochMilli();

        Appointment app = new Appointment();
        app.setId(1L);
        app.setAppointmentState(AppointmentState.RESERVED);
        app.setStartTime(currTime);
        app.setEndTime(Instant.ofEpochMilli(currTime).plus(15, ChronoUnit.MINUTES).toEpochMilli());

        Mockito.when(appointmentRepositoryMock.findById(1L)).thenReturn(Optional.of(app));

        assertTrue(appointmentServiceMock.startAppointment(1L));
        assertEquals(AppointmentState.IN_PROGRESS, app.getAppointmentState());

        Appointment app2 = new Appointment();
        app2.setId(2L);
        app2.setAppointmentState(AppointmentState.RESERVED);
        app2.setStartTime(Instant.ofEpochMilli(currTime).minus(20, ChronoUnit.MINUTES).toEpochMilli());
        app2.setEndTime(Instant.ofEpochMilli(currTime).plus(15, ChronoUnit.MINUTES).toEpochMilli());

        Mockito.when(appointmentRepositoryMock.findById(2L)).thenReturn(Optional.of(app2));

        assertFalse(appointmentServiceMock.startAppointment(2L));

        Appointment app3 = new Appointment();
        app3.setId(3L);
        app3.setAppointmentState(AppointmentState.FINISHED);
        app3.setStartTime(currTime);
        app3.setEndTime(Instant.ofEpochMilli(currTime).plus(15, ChronoUnit.MINUTES).toEpochMilli());

        Mockito.when(appointmentRepositoryMock.findById(3L)).thenReturn(Optional.of(app3));

        assertFalse(appointmentServiceMock.startAppointment(3L));

        Appointment app4 = new Appointment();
        app4.setId(4L);
        app4.setAppointmentState(AppointmentState.RESERVED);
        app4.setStartTime(Instant.ofEpochMilli(currTime).plus(20, ChronoUnit.MINUTES).toEpochMilli());
        app4.setEndTime(Instant.ofEpochMilli(currTime).plus(40, ChronoUnit.MINUTES).toEpochMilli());

        Mockito.when(appointmentRepositoryMock.findById(4L)).thenReturn(Optional.of(app4));

        assertFalse(appointmentServiceMock.startAppointment(4L));
    }
}
