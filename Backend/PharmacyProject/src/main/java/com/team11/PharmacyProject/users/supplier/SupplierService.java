package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.dto.supplier.SupplierStockItemDTO;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SupplierService {
    List<SupplierItem> getStockForId(long id);

    boolean insertStockItem(long supplierId, SupplierStockItemDTO stockItemDTO);

    boolean updateStockItem(long id, SupplierStockItemDTO stockItemDTO);
}
