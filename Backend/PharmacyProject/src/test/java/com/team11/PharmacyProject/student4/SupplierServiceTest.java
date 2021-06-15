package com.team11.PharmacyProject.student4;

import com.team11.PharmacyProject.dto.offer.OfferListDTO;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineRepository;
import com.team11.PharmacyProject.myOrder.MyOrder;
import com.team11.PharmacyProject.myOrder.MyOrderRepository;
import com.team11.PharmacyProject.offer.OfferRepository;
import com.team11.PharmacyProject.offer.OfferService;
import com.team11.PharmacyProject.orderItem.OrderItem;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import com.team11.PharmacyProject.priceList.PriceListRepository;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import com.team11.PharmacyProject.supplierItem.SupplierItemRepository;
import com.team11.PharmacyProject.users.supplier.Supplier;
import com.team11.PharmacyProject.users.supplier.SupplierRepository;
import com.team11.PharmacyProject.users.supplier.SupplierServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class SupplierServiceTest {

    @InjectMocks
    SupplierServiceImpl supplierServiceMock;
    @Mock
    private SupplierRepository supplierRepositoryMock;
    @Mock
    private MyOrderRepository myOrderRepositoryMock;
    @Mock
    private SupplierItemRepository supplierItemRepositoryMock;

    @Test()
    @Transactional
    @Rollback
    public void AddOfferTest() {
        // Medicine
        var m1 = new Medicine();
        m1.setCode("KOD1");
        m1.setId(1L);
        var m2 = new Medicine();
        m2.setCode("KOD2");
        m2.setId(2L);

        // Order Items
        var oi1 = new OrderItem();
        oi1.setAmount(30);
        oi1.setId(1L);
        oi1.setMedicine(m1);
        var oi2 = new OrderItem();
        oi2.setAmount(20);
        oi2.setId(2L);
        oi2.setMedicine(m2);

        // Order Items
        var orderItems = new ArrayList<OrderItem>();
        orderItems.add(oi1);
        orderItems.add(oi2);

        // Supplier Items
        var si1 = new SupplierItem();
        si1.setAmount(100);
        si1.setMedicine(m1);
        var si2 = new SupplierItem();
        si2.setAmount(100);
        si2.setMedicine(m2);

        // Order
        var order = new MyOrder();
        order.setId(3L);
        order.setDeadline(1624769315924L);
        order.setOrderItem(orderItems);

        // Offer
        var offerListDTO = new OfferListDTO();
        offerListDTO.setOrderId(order.getId());
        offerListDTO.setId(2L);
        offerListDTO.setPrice(1000);

        var supp = new Supplier();
        supp.setId(1L);
        supp.setOffers(new ArrayList<>());

        Mockito.when(supplierRepositoryMock.findSupplierWithOffersUsingId(supp.getId())).thenReturn(Optional.of(supp));
        Mockito.when(myOrderRepositoryMock.getMyOrderById(order.getId())).thenReturn(Optional.of(order));
        Mockito.when(supplierItemRepositoryMock.getSupplierItemBySupplierIdCodeAmount(supp.getId(), m1.getCode(), oi1.getAmount())).thenReturn(Optional.of(si1));
        Mockito.when(supplierItemRepositoryMock.getSupplierItemBySupplierIdCodeAmount(supp.getId(), m2.getCode(), oi2.getAmount())).thenReturn(Optional.of(si2));
        try {
            supplierServiceMock.insertOffer(supp.getId(), offerListDTO);
            Assertions.assertEquals(70, si1.getAmount());
            Assertions.assertEquals(80, si2.getAmount());
        } catch (Exception e) {
            Assertions.fail();
        }

    }

    @Test()
    @Transactional
    @Rollback
    public void AddOfferMedicineTest() {
        // Medicine
        var m1 = new Medicine();
        m1.setCode("KOD1");
        m1.setId(1L);
        var m2 = new Medicine();
        m2.setCode("KOD2");
        m2.setId(2L);
        var m3 = new Medicine();
        m3.setCode("KOD3");
        m3.setId(3L);

        // Order Items
        var oi1 = new OrderItem();
        oi1.setAmount(30);
        oi1.setId(1L);
        oi1.setMedicine(m1);
        var oi2 = new OrderItem();
        oi2.setAmount(20);
        oi2.setId(2L);
        oi2.setMedicine(m2);

        // Order Items
        var orderItems = new ArrayList<OrderItem>();
        orderItems.add(oi1);
        orderItems.add(oi2);

        // Supplier Items - Ima lekova 2 i 3, ali me i lek 1
        var si1 = new SupplierItem();
        si1.setAmount(100);
        si1.setMedicine(m3);
        var si2 = new SupplierItem();
        si2.setAmount(100);
        si2.setMedicine(m2);

        // Order
        var order = new MyOrder();
        order.setId(3L);
        order.setDeadline(1624769315924L);
        order.setOrderItem(orderItems);

        // Offer
        var offerListDTO = new OfferListDTO();
        offerListDTO.setOrderId(order.getId());
        offerListDTO.setId(2L);
        offerListDTO.setPrice(1000);

        var supp = new Supplier();
        supp.setId(1L);
        supp.setOffers(new ArrayList<>());

        Mockito.when(supplierRepositoryMock.findSupplierWithOffersUsingId(supp.getId())).thenReturn(Optional.of(supp));
        Mockito.when(myOrderRepositoryMock.getMyOrderById(order.getId())).thenReturn(Optional.of(order));
        Mockito.when(supplierItemRepositoryMock.getSupplierItemBySupplierIdCodeAmount(supp.getId(), m1.getCode(), oi1.getAmount())).thenReturn(Optional.of(si1));
        Mockito.when(supplierItemRepositoryMock.getSupplierItemBySupplierIdCodeAmount(supp.getId(), m2.getCode(), oi2.getAmount())).thenReturn(Optional.of(si2));
        try {
            supplierServiceMock.insertOffer(supp.getId(), offerListDTO);
            Assertions.assertEquals(70, si1.getAmount());
            Assertions.assertEquals(80, si2.getAmount());
        } catch (Exception e) {
            Assertions.fail();
        }

    }

    @Test()
    @Transactional
    @Rollback
    public void AddOfferStockFailTest() {
        // Medicine
        var m1 = new Medicine();
        m1.setCode("KOD1");
        m1.setId(1L);
        var m2 = new Medicine();
        m2.setCode("KOD2");
        m2.setId(2L);

        // Order Items
        var oi1 = new OrderItem();
        oi1.setAmount(30);
        oi1.setId(1L);
        oi1.setMedicine(m1);
        var oi2 = new OrderItem();
        oi2.setAmount(20);
        oi2.setId(2L);
        oi2.setMedicine(m2);

        // Order Items
        var orderItems = new ArrayList<OrderItem>();
        orderItems.add(oi1);
        orderItems.add(oi2);

        // Supplier Items
        var si1 = new SupplierItem();
        si1.setAmount(29); // Ima manje nego sto treba!
        si1.setMedicine(m1);
        var si2 = new SupplierItem();
        si2.setAmount(100);
        si2.setMedicine(m2);

        // Order
        var order = new MyOrder();
        order.setId(3L);
        order.setDeadline(1624769315924L);
        order.setOrderItem(orderItems);

        // Offer
        var offerListDTO = new OfferListDTO();
        offerListDTO.setOrderId(order.getId());
        offerListDTO.setId(2L);
        offerListDTO.setPrice(1000);

        var supp = new Supplier();
        supp.setId(1L);
        supp.setOffers(new ArrayList<>());

        Mockito.when(supplierRepositoryMock.findSupplierWithOffersUsingId(supp.getId())).thenReturn(Optional.of(supp));
        Mockito.when(myOrderRepositoryMock.getMyOrderById(order.getId())).thenReturn(Optional.of(order));
        Mockito.when(supplierItemRepositoryMock.getSupplierItemBySupplierIdCodeAmount(supp.getId(), m1.getCode(), oi1.getAmount())).thenReturn(null);
        Mockito.when(supplierItemRepositoryMock.getSupplierItemBySupplierIdCodeAmount(supp.getId(), m2.getCode(), oi2.getAmount())).thenReturn(Optional.of(si2));
        try {
            supplierServiceMock.insertOffer(supp.getId(), offerListDTO);
            Assertions.fail();
        } catch (Exception e) {
            System.out.println(";)");
        }

    }
}
