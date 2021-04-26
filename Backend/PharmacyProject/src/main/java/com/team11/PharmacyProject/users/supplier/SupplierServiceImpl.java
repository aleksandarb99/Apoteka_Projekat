package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.dto.supplier.SupplierStockItemDTO;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineRepository;
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

    @Override
    public List<SupplierItem> getStockForId(long id) {
        Optional<Supplier> supplier = supplierRepository.findSupplierWithSupplierItemsId(id);
        if (supplier.isPresent()) {
            return supplier.get().getSupplierItems();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean insertStockItem(long supplierId, SupplierStockItemDTO stockItemDTO) {
        // TODO validation
        Optional<Supplier> s = supplierRepository.findSupplierWithSupplierItemsId(supplierId);
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
                if (contains)
                    return false;
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
        Optional<Supplier> s = supplierRepository.findSupplierWithSupplierItemsId(id);
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
}
