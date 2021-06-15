package com.team11.PharmacyProject.student2;

import com.team11.PharmacyProject.advertisement.AdvertismentService;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemRepository;
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
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceListServiceTest {

    @Mock
    private PriceListRepository priceListRepositoryMock;

    @Mock
    private MedicineService medicineServiceMock;

    @Mock
    private MedicineItemRepository medicineItemRepositoryMock;

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

        Assertions.assertThrows(RuntimeException.class, () -> priceListServiceMock.insertMedicine(1L, 2L, 1000));
    }

    @Test()
    @Transactional
    @Rollback(value = true)
    public void removeMedicineTest() {

        PriceList p1 = new PriceList();
        p1.setId(1L);
        List<MedicineItem> list = new ArrayList<>();
        MedicineItem mi = new MedicineItem();
        mi.setId(1L);
        list.add(mi);
        p1.setMedicineItems(list);

        Mockito.when(priceListRepositoryMock.findByIdAndFetchMedicineItems(1L)).thenReturn(p1);
        Mockito.when(medicineItemServiceMock.findById(1L)).thenReturn(mi);
        Mockito.when(medicineReservationServiceMock.isMedicineItemReserved(1L)).thenReturn(false);

        priceListServiceMock.removeMedicine(1L, 1L);
        Assertions.assertEquals(0, p1.getMedicineItems().size());
    }

}
