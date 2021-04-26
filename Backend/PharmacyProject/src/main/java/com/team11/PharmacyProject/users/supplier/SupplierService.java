package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.dto.offer.OfferListDTO;
import com.team11.PharmacyProject.dto.supplier.SupplierStockItemDTO;
import com.team11.PharmacyProject.offer.Offer;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SupplierService {
    List<SupplierItem> getStockForId(long id);

    boolean insertStockItem(long supplierId, SupplierStockItemDTO stockItemDTO);

    boolean updateStockItem(long id, SupplierStockItemDTO stockItemDTO);

    List<OfferListDTO> getOffersForId(long supplierId);

    boolean insertOrder(long id, OfferListDTO offerDTO);
}
