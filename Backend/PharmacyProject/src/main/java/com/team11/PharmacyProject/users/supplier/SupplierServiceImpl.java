package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<SupplierItem> getStockForId(long id) {
        Optional<Supplier> supplier = supplierRepository.findSupplierWithSupplierItemsId(id);
        if (supplier.isPresent()) {
            return supplier.get().getSupplierItems();
        } else {
            return new ArrayList<>();
        }
    }
}
