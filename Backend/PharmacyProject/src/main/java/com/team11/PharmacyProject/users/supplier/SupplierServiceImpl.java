package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.dto.offer.OfferListDTO;
import com.team11.PharmacyProject.dto.supplier.SupplierStockItemDTO;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.enums.OfferState;
import com.team11.PharmacyProject.enums.OrderState;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineRepository;
import com.team11.PharmacyProject.myOrder.MyOrder;
import com.team11.PharmacyProject.myOrder.MyOrderRepository;
import com.team11.PharmacyProject.offer.Offer;
import com.team11.PharmacyProject.offer.OfferService;
import com.team11.PharmacyProject.orderItem.OrderItem;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private MyOrderRepository myOrderRepository;

    @Autowired
    private OfferService offerService;

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    EmailService emailService;

    @Override
    public List<SupplierItem> getStockForId(long id) {
        Optional<Supplier> supplier = supplierRepository.findSupplierWithSupplierItemsUsingId(id);
        if (supplier.isPresent()) {
            return supplier.get().getSupplierItems();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean insertStockItem(long supplierId, SupplierStockItemDTO stockItemDTO) {
        // TODO validation
        Optional<Supplier> s = supplierRepository.findSupplierWithSupplierItemsUsingId(supplierId);
        if (s.isEmpty()) {
            return false;
        }
        Supplier supp = s.get();

        if (stockItemDTO != null) {
            SupplierItem supplierItem = new SupplierItem();
            Optional<Medicine> medicine = medicineRepository.findById(stockItemDTO.getMedicineId());
            // Ako lek postoji i nije vec dodat
            if (medicine.isPresent()) {
                boolean contains = supp.getSupplierItems().stream().anyMatch(si -> si.getMedicine().getId().equals(medicine.get().getId()));
                if (contains) {
                    Optional<SupplierItem> oldSupplierItem = supp.getSupplierItems().stream()
                            .filter(si -> si.getMedicine().getId().equals(medicine.get().getId()))
                            .collect(Collectors.toList()).stream().findFirst();
                    int oldAmount = 0;
                    if (oldSupplierItem.isPresent()) {
                        oldAmount += oldSupplierItem.get().getAmount();
                    }
                    stockItemDTO.setAmount(stockItemDTO.getAmount() + oldAmount);
                    return updateStockItem(supplierId, stockItemDTO);
                }
                supplierItem.setAmount(stockItemDTO.getAmount());
                supplierItem.setMedicine(medicine.get());
                supp.getSupplierItems().add(supplierItem);
                supplierRepository.save(supp);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateStockItem(long id, SupplierStockItemDTO stockItemDTO) {
        // TODO validation (amount granice, ko menja)
        Optional<Supplier> s = supplierRepository.findSupplierWithSupplierItemsUsingId(id);
        if (s.isEmpty()) {
            return false;
        }
        Supplier supp = s.get();

        if (stockItemDTO != null) {
            Optional<Medicine> medicine = medicineRepository.findById(stockItemDTO.getMedicineId());
            // Ako lek postoji i nije vec dodat
            if (medicine.isPresent()) {
                boolean contains = supp.getSupplierItems().stream().anyMatch(si -> si.getMedicine().getId().equals(medicine.get().getId()));
                // Lek mora biti dodat
                if (!contains)
                    return false;
                Optional<SupplierItem> SIToUpdate = supp.getSupplierItems()
                        .stream().filter(si -> si.getMedicine().getId().equals(medicine.get().getId()))
                        .collect(Collectors.toList()).stream().findFirst();
                if (SIToUpdate.isEmpty())
                    return false;
                SIToUpdate.get().setAmount(stockItemDTO.getAmount());
                supplierRepository.save(supp);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean acceptOffer(Long selectedOfferId, Long orderId) {
        Map<String, List<Offer>> offersForOrder = getOffersByOrderId(orderId);

        Optional<MyOrder> order = myOrderRepository.getMyOrderById(orderId);
        if(order.isEmpty())
            return false;

        boolean flag = true;

        String email = "abuljevic8@gmail.com";

        for (String key:offersForOrder.keySet()) {
            List<Offer> offers = offersForOrder.get(key);

            for (Offer o:offers) {
                if(o.getId().equals(selectedOfferId)) {
                    o.setOfferState(OfferState.ACCEPTED);
                    try {
                        emailService.notifySupplierThatHisOfferIsAccepted(email, order.get(), key);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                    offerService.save(o);
                }
                else {
                    o.setOfferState(OfferState.DENIED);
                    try {
                        emailService.notifySupplierThatHisOfferIsRefused(email, order.get(), key);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                    offerService.save(o);
                }
            }
        }

        MyOrder order1 = order.get();
        order1.setOrderState(OrderState.ENDED);
        myOrderRepository.save(order1);

        pharmacyService.addMedicineToStock(order1);

//        TODO proveri da li se trebaju deaktivirati inquries

        return true;
    }

    @Override
    public Map<String, List<Offer>> getOffersByOrderId(long orderId) {
        List<Supplier> supplierList = supplierRepository.findAllWithOffers();
        Map<String, List<Offer>> offerMap = new HashMap();

        for (Supplier s:supplierList) {
            String key = s.getFirstName()+" " + s.getLastName();
            for (Offer o:s.getOffers()) {
                if(o.getOrder().getId().equals(orderId) && o.getOfferState().equals(OfferState.PENDING)) {
                    if(offerMap.containsKey(key))
                        offerMap.get(key).add(o);
                    else
                        offerMap.put(key, new ArrayList<Offer>());
                        offerMap.get(key).add(o);
                }
            }
        }

        return offerMap;
    }

    @Override
    public List<OfferListDTO> getOffersForId(long supplierId) {
        Optional<Supplier> supplier = supplierRepository.findSupplierWithOffersUsingId(supplierId);
        return supplier.map(value -> value.getOffers().stream()
                .map(o -> new OfferListDTO(o.getId(), o.getPrice(), o.getDeliveryDate(), o.getOfferState(), o.getOrder().getId()))
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public boolean insertOffer(long suppId, OfferListDTO offerDTO) {
        // TODO validation
        Optional<Supplier> s = supplierRepository.findSupplierWithOffersUsingId(suppId);
        if (s.isEmpty()) {
            return false;
        }
        Supplier supp = s.get();

        if (offerDTO != null) {
            // Ako je cena negativna, onda nije okej
            if (offerDTO.getPrice() < 0) {
                return false;
            }
            // Dobavi order za offer
            Optional<MyOrder> order = myOrderRepository.getMyOrderById(offerDTO.getOrderId());
            if (order.isEmpty())
                return false;
            // Ako je orderId vec u offerima, onda nemoj da dodajes
//            boolean contains = supp.getOffers().stream().anyMatch(o -> o.getOrder().getId().equals(order.get().getId()));
//            if (contains)
//                return false;
            // Ako je prosao, onda ne moze da se doda (150s ogranicenja)
            if (order.get().getDeadline() < (System.currentTimeMillis() + 150000))
                return false;
            // Da li je na stanju sve
            if (!onStock(suppId, order.get())) {
                return false;
            }
            Offer offer = new Offer();
            offer.setOfferState(offerDTO.getOfferState());
            offer.setDeliveryDate(offerDTO.getDeliveryDate());
            offer.setPrice(offerDTO.getPrice());
            offer.setOrder(order.get());
            supp.getOffers().add(offer);
            supplierRepository.save(supp);
            return true;
            }
        return false;
    }

    @Override
    public boolean updateOffer(long suppId, OfferListDTO offerDTO) {
        // TODO validation
        Optional<Supplier> s = supplierRepository.findSupplierWithOffersUsingId(suppId);
        if (s.isEmpty()) {
            return false;
        }
        Supplier supp = s.get();

        if (offerDTO != null) {
            // Ako je cena negativna, onda nije okej
            if (offerDTO.getPrice() < 0) {
                return false;
            }
            // Dobavi order za offer
            Optional<MyOrder> order = myOrderRepository.getMyOrderById(offerDTO.getOrderId());
            if (order.isEmpty())
                return false;
            // Mora da bude ista porudzbina
            if (order.get().getId() != offerDTO.getOrderId())
                return false;
            // Ako je prosao, onda ne moze da se menja (150s ogranicenja)
            if (order.get().getDeadline() < (System.currentTimeMillis()))
                return false;
            // Da li je na stanju sve
            if (!onStock(suppId, order.get())) {
                return false;
            }
            Optional<Offer> offerToUpdate = supp.getOffers().stream()
                    .filter(o -> o.getOrder().getId().equals(offerDTO.getOrderId()))
                    .collect(Collectors.toList()).stream().findFirst();

            if (offerToUpdate.isEmpty())
                return false;
            offerToUpdate.get().setPrice(offerDTO.getPrice());
            offerToUpdate.get().setDeliveryDate(offerDTO.getDeliveryDate());
            offerToUpdate.get().setOfferState(offerDTO.getOfferState());
            supplierRepository.save(supp);
            return true;
        }
        return false;
    }

    private boolean onStock(long supplierId, MyOrder order) {
        Optional<Supplier> s = supplierRepository.findSupplierWithSupplierItemsUsingId(supplierId);
        if (s.isEmpty()) {
            return false;
        }
        List<SupplierItem> sil = s.get().getSupplierItems();
        // Da li je svaka stavka iz ordera prisutna kod suppliera
        for (OrderItem oi: order.getOrderItem()) {
            boolean onStock = sil.stream()
                    .anyMatch(si -> si.getMedicine().getId().equals(oi.getMedicine().getId()) &&
                                    si.getAmount() >= oi.getAmount());
            if (!onStock) return false;
        }
        return true;
    }
}
