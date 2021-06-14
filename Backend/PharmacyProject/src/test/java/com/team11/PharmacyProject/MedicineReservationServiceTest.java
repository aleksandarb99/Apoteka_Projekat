package com.team11.PharmacyProject;

import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemRepository;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservationRepository;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservationServiceImpl;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.rankingCategory.RankingCategory;
import com.team11.PharmacyProject.rankingCategory.RankingCategoryServiceImpl;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicineReservationServiceTest {

    @InjectMocks
    MedicineReservationServiceImpl reservationService;

    @Mock
    PharmacyRepository pharmacyRepositoryMock;

    @Mock
    MedicineReservationRepository reservationRepositoryMock;

    @Mock
    MedicineItemRepository itemRepositoryMock;

    @Mock
    PatientRepository patientRepositoryMock;

    @Mock
    RankingCategoryServiceImpl categoryServiceMock;

    @Test()
    @Transactional
    @Rollback(value = true)
    public void insertMedicineReservation() {

        Patient patient = new Patient();
        patient.setFirstName("Mika");
        patient.setLastName("Mikic");
        patient.setEmail("mikamikic@gmail.com");
        patient.setId(11L);
        patient.setPenalties(1);
        patient.setPoints(80);
        patient.setMedicineReservation(new ArrayList<>());

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(3L);
        pharmacy.setName("Test apoteka");
        pharmacy.setAddress(null);

        PriceList list = new PriceList();
        list.setId(2L);

        MedicineItem item = new MedicineItem();
        item.setId(1L);
        item.setAmount(10);

        Medicine medicine = new Medicine();
        medicine.setId(4L);

        item.setMedicine(medicine);
        list.setMedicineItems(new ArrayList<>());
        list.addMedicineItem(item);
        pharmacy.setPriceList(list);

        MedicineReservationInsertDTO dto = new MedicineReservationInsertDTO();
        dto.setMedicineId(4L);
        dto.setPharmacyId(3L);
        dto.setUserId(11L);
        dto.setPickupDate(1654943977000L);
        dto.setPrice(1000);

        RankingCategory category = new RankingCategory();
        category.setDiscount(10);

        Mockito.when(patientRepositoryMock.findById(11L)).thenReturn(Optional.of(patient));
        Mockito.when(pharmacyRepositoryMock.findPharmacyByPharmacyAndMedicineId(3L, 4L)).thenReturn(pharmacy);
        Mockito.when(itemRepositoryMock.findByIdForTransaction(1L)).thenReturn(item);
        Mockito.when(categoryServiceMock.getCategoryByPoints(80)).thenReturn(category);
        Mockito.when(patientRepositoryMock.findByIdAndFetchReservationsEagerly(11L)).thenReturn(patient);
        Mockito.when(itemRepositoryMock.save(item)).thenReturn(item);
        Mockito.when(patientRepositoryMock.save(patient)).thenReturn(patient);

        reservationService.insertMedicineReservation(dto);

        assertEquals(9, item.getAmount());
        assertEquals(900, patient.getMedicineReservation().get(0).getPrice(), 0.0);

        Mockito.verify(itemRepositoryMock, Mockito.times(1)).save(item);
        Mockito.verify(patientRepositoryMock, Mockito.times(1)).save(patient);
    }

    @Test(expected = RuntimeException.class)
    @Transactional
    @Rollback(value = true)
    public void insertMedicineReservationFail() {

        Patient patient = new Patient();
        patient.setFirstName("Mika");
        patient.setLastName("Mikic");
        patient.setEmail("mikamikic@gmail.com");
        patient.setId(11L);
        patient.setPenalties(1);
        patient.setPoints(80);
        patient.setMedicineReservation(new ArrayList<>());

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(3L);
        pharmacy.setName("Test apoteka");
        pharmacy.setAddress(null);

        PriceList list = new PriceList();
        list.setId(2L);

        MedicineItem item = new MedicineItem();
        item.setId(1L);
        item.setAmount(0);

        Medicine medicine = new Medicine();
        medicine.setId(4L);

        item.setMedicine(medicine);
        list.setMedicineItems(new ArrayList<>());
        list.addMedicineItem(item);
        pharmacy.setPriceList(list);

        MedicineReservationInsertDTO dto = new MedicineReservationInsertDTO();
        dto.setMedicineId(4L);
        dto.setPharmacyId(3L);
        dto.setUserId(11L);
        dto.setPickupDate(1654943977000L);
        dto.setPrice(1000);

        Mockito.when(patientRepositoryMock.findById(11L)).thenReturn(Optional.of(patient));
        Mockito.when(pharmacyRepositoryMock.findPharmacyByPharmacyAndMedicineId(3L, 4L)).thenReturn(pharmacy);
        Mockito.when(itemRepositoryMock.findByIdForTransaction(1L)).thenReturn(item);

        reservationService.insertMedicineReservation(dto);
    }

    @Test()
    @Transactional
    @Rollback(value = true)
    public void cancelReservation() {

        MedicineReservation reservation = new MedicineReservation();
        reservation.setId(1L);
        reservation.setState(ReservationState.RESERVED);
        reservation.setPickupDate(1654943977000L);

        MedicineItem item = new MedicineItem();
        item.setId(5L);
        item.setAmount(10);

        reservation.setMedicineItem(item);

        Mockito.when(reservationRepositoryMock.findByIdForCanceling(1L)).thenReturn(reservation);
        Mockito.when(itemRepositoryMock.findById(5L)).thenReturn(Optional.of(item));
        Mockito.when(itemRepositoryMock.save(item)).thenReturn(item);
        Mockito.when(reservationRepositoryMock.save(reservation)).thenReturn(reservation);

        reservationService.cancelReservation(1L);

        assertEquals(11, item.getAmount());

        Mockito.verify(itemRepositoryMock, Mockito.times(1)).save(item);
        Mockito.verify(reservationRepositoryMock, Mockito.times(1)).save(reservation);
    }


}
