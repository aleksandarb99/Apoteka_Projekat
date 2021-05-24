package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.dto.offer.OfferListDTO;
import com.team11.PharmacyProject.dto.supplier.SupplierStockItemDTO;
import com.team11.PharmacyProject.offer.Offer;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface SupplierService {
    List<SupplierItem> getStockForId(long id);

    void insertStockItem(long supplierId, SupplierStockItemDTO stockItemDTO) throws Exception;

    void updateStockItem(long id, SupplierStockItemDTO stockItemDTO) throws Exception;

    List<OfferListDTO> getOffersForId(long supplierId);

    boolean insertOffer(long id, OfferListDTO offerDTO);

    boolean updateOffer(long id, OfferListDTO offerDTO);

    Map<String, List<Offer>> getOffersByOrderId(long orderId);

    void acceptOffer(Long selectedOfferId, Long orderId);
}
