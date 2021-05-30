package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.dto.offer.OfferAcceptDTO;
import com.team11.PharmacyProject.dto.offer.OfferWithWorkerDTO;
import com.team11.PharmacyProject.dto.supplier.SupplierStockItemDTO;
import com.team11.PharmacyProject.dto.offer.OfferListDTO;
import com.team11.PharmacyProject.dto.workplace.WorkplaceDTOWithWorkdays;
import com.team11.PharmacyProject.enums.OfferState;
import com.team11.PharmacyProject.exceptions.CustomException;
import com.team11.PharmacyProject.offer.Offer;
import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ModelMapper modelMapper;

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
        try {
            supplierService.insertStockItem(id, stockItemDTO);
            return new ResponseEntity<>("Stock added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="/stock/{id}")
    public ResponseEntity<String> updateStockAmount(@PathVariable("id") long id, @RequestBody SupplierStockItemDTO stockItemDTO) {
        try {
            supplierService.updateStockItem(id, stockItemDTO);
            return new ResponseEntity<>("Stock updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error. Stock item not updated", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/offers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OfferListDTO>> getOffers(@PathVariable("id") long supplierId, @RequestParam(required = false) OfferState type) {
        List<OfferListDTO> supplierOffers = supplierService.getOffersForId(supplierId);
        if (type != null) {
            supplierOffers = supplierOffers.stream().filter(offerListDTO -> offerListDTO.getOfferState() == type).collect(Collectors.toList());
        }
        return new ResponseEntity<>(supplierOffers, HttpStatus.OK);
    }

    @GetMapping(value="/offers/byorderid/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<?> getOffersByOrderId(@PathVariable("orderId") long orderId) {
        Map<String, List<Offer>> map = supplierService.getOffersByOrderId(orderId);

        List<OfferWithWorkerDTO> offersOfferListDTOS = new ArrayList<>();
        List<OfferWithWorkerDTO> offersList;

        for (String s:map.keySet()) {
            List<Offer> list = map.get(s);
            offersList = list.stream().map(m -> modelMapper.map(m, OfferWithWorkerDTO.class)).collect(Collectors.toList());
            for (OfferWithWorkerDTO item:offersList) {
                item.setWorker(s);
                offersOfferListDTOS.add(item);
            }
        }
        return new ResponseEntity<>(offersOfferListDTOS, HttpStatus.OK);

    }

    @PostMapping(value="/offers/accept/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<String> acceptOffer(@RequestBody OfferAcceptDTO dto) {
        try {
            supplierService.acceptOffer(dto.getSelectedOfferId(), dto.getOrderId());
            return new ResponseEntity<>("Offer is successfully accepted!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/offers/{id}")
    public ResponseEntity<String> addOffer(@PathVariable("id") long id, @RequestBody OfferListDTO offerDTO) {
        // Uvek ce biti pending kada treba da se doda
        offerDTO.setOfferState(OfferState.PENDING);
        try {
            supplierService.insertOffer(id, offerDTO);
            return new ResponseEntity<>("Offer added successfully", HttpStatus.OK);
        } catch (PessimisticLockingFailureException e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops! Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="/offers/{id}")
    public ResponseEntity<String> updateOffer(@PathVariable("id") long id, @RequestBody OfferListDTO offerDTO) {
        try {
            supplierService.updateOffer(id, offerDTO);
            return new ResponseEntity<>("Offer updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
