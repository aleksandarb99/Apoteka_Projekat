package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.dto.offer.OfferListDTO;
import com.team11.PharmacyProject.dto.supplier.SupplierStockItemDTO;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineRepository;
import com.team11.PharmacyProject.myOrder.MyOrder;
import com.team11.PharmacyProject.myOrder.MyOrderRepository;
import com.team11.PharmacyProject.offer.Offer;
import com.team11.PharmacyProject.orderItem.OrderItem;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private MyOrderRepository myOrderRepository;

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
    public List<OfferListDTO> getOffersForId(long supplierId) {
        Optional<Supplier> supplier = supplierRepository.findSupplierWithOffersUsingId(supplierId);
        return supplier.map(value -> value.getOffers().stream()
                .map(o -> new OfferListDTO(o.getId(), o.getPrice(), o.getDeliveryDate(), o.getOfferState(), o.getOrder().getId()))
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public boolean insertOrder(long suppId, OfferListDTO offerDTO) {
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
