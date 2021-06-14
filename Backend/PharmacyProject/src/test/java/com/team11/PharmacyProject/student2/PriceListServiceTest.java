package com.team11.PharmacyProject.student2;

import com.team11.PharmacyProject.advertisement.AdvertismentService;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemService;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservationService;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.priceList.PriceListRepository;
import com.team11.PharmacyProject.priceList.PriceListServiceImpl;
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

import java.util.ArrayList;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceListServiceTest {

    @Mock
    private PriceListRepository priceListRepositoryMock;

    @Mock
    private MedicineService medicineServiceMock;

    @Mock
    private MedicineItemService medicineItemServiceMock;

    @Mock
    private MedicineReservationService medicineReservationServiceMock;

    @Mock
    private AdvertismentService advertismentServiceMock;

    @InjectMocks
    private PriceListServiceImpl priceListServiceMock;

    @Test()
    @Transactional
    @Rollback(value = true)
    public void insertMedicineTest() {

        PriceList p1 = new PriceList();
        p1.setId(1L);
        p1.setMedicineItems(new ArrayList<>());

        Medicine m1 = new Medicine();
        m1.setId(1L);

        Mockito.when(priceListRepositoryMock.findByIdAndFetchMedicineItems(1L)).thenReturn(p1);
        Mockito.when(medicineServiceMock.findOne(1L)).thenReturn(m1);
        Mockito.when(priceListRepositoryMock.save(p1)).thenReturn(p1);

        priceListServiceMock.insertMedicine(1L, 1L, 1000);

        Assertions.assertEquals(1, p1.getMedicineItems().size());
        Assertions.assertEquals(1L, p1.getMedicineItems().get(0).getMedicine().getId(), 0.0);

        Mockito.verify(priceListRepositoryMock, Mockito.times(1)).save(p1);
    }

    @Test()
    @Transactional
    @Rollback(value = true)
    public void insertMedicineTestFail() {

        PriceList p1 = new PriceList();
        p1.setId(1L);

        ArrayList<MedicineItem> list = new ArrayList<>();

        MedicineItem mi1 = new MedicineItem();
        Medicine m1 = new Medicine();
        m1.setId(1L);
        mi1.setMedicine(m1);

        MedicineItem mi2 = new MedicineItem();
        Medicine m2 = new Medicine();
        m2.setId(2L);
        mi2.setMedicine(m2);

        list.add(mi1);
        list.add(mi2);

        p1.setMedicineItems(list);

        Mockito.when(priceListRepositoryMock.findByIdAndFetchMedicineItems(1L)).thenReturn(p1);
        Mockito.when(medicineServiceMock.findOne(2L)).thenReturn(m2);
        Mockito.when(priceListRepositoryMock.save(p1)).thenReturn(p1);

        Assertions.assertThrows(RuntimeException.class, () -> priceListServiceMock.insertMedicine(1L, 2L, 1000));
    }

}
