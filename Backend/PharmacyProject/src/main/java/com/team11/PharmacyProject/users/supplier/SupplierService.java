package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SupplierService {
    List<SupplierItem> getStockForId(long id);
}
