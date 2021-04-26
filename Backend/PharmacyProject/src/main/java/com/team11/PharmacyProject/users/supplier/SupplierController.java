package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.dto.supplier.SupplierStockItemDTO;
import com.team11.PharmacyProject.dto.offer.OfferListDTO;
import com.team11.PharmacyProject.enums.OfferState;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping(value="/stock/{id}")
    public ResponseEntity<List<SupplierStockItemDTO>> getStock(@PathVariable("id") long supplierId) {
        List<SupplierItem> supplierStock = supplierService.getStockForId(supplierId);
        List<SupplierStockItemDTO> supplierStockItemDTOS = supplierStock.stream()
                .map(si -> new SupplierStockItemDTO(si.getMedicine().getId(), si.getMedicine().getName(), si.getAmount()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(supplierStockItemDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/stock/{id}")
    public ResponseEntity<String> addItemToStock(@PathVariable("id") long id, @RequestBody SupplierStockItemDTO stockItemDTO) {
        if (supplierService.insertStockItem(id, stockItemDTO)) {
            return new ResponseEntity<>("Stock added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. Stock item not added", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="/stock/{id}")
    public ResponseEntity<String> updateStockAmount(@PathVariable("id") long id, @RequestBody SupplierStockItemDTO stockItemDTO) {
        if (supplierService.updateStockItem(id, stockItemDTO)) {
            return new ResponseEntity<>("Stock updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. Stock item not updated", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/offers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OfferListDTO>> getOffers(@PathVariable("id") long supplierId) {
        List<OfferListDTO> supplierOffers = supplierService.getOffersForId(supplierId);
        return new ResponseEntity<>(supplierOffers, HttpStatus.OK);
    }

    @PostMapping(value="/offers/{id}")
    public ResponseEntity<String> addOffer(@PathVariable("id") long id, @RequestBody OfferListDTO offerDTO) {
        // Uvek ce biti pending kada treba da se doda
        offerDTO.setOfferState(OfferState.PENDING);
        if (supplierService.insertOffer(id, offerDTO)) {
            return new ResponseEntity<>("Offer added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. Offer not added", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="/offers/{id}")
    public ResponseEntity<String> updateOffer(@PathVariable("id") long id, @RequestBody OfferListDTO offerDTO) {
        if (supplierService.updateOffer(id, offerDTO)) {
            return new ResponseEntity<>("Offer updted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. Offer not updated", HttpStatus.BAD_REQUEST);
        }
    }
}
