package com.team11.PharmacyProject.priceList;

import com.team11.PharmacyProject.dto.medicine.MedicineItemDTO;
import com.team11.PharmacyProject.dto.pricelist.PriceListDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pricelist")
public class PriceListController {

    @Autowired
    PriceListService priceListService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPriceListById(@PathVariable("id") Long id) {
        PriceList priceList = priceListService.findByIdAndFetchMedicineItems(id);

        if (priceList == null) {
            return new ResponseEntity<>("Price list with id " + id + " does not exist!", HttpStatus.BAD_REQUEST);
        }
        PriceListDTO dto = convertToDto(priceList);
        for (MedicineItemDTO item : dto.getMedicineItems()) {
            item.setPrice(priceListService.getMedicineItemPrice(item.getId(), id));
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/addmedicine/{medicineId}/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<?> insertMedicine(@PathVariable("id") Long id, @PathVariable("medicineId") Long medicineId, @PathVariable("price") int price) {
        try {
            priceListService.insertMedicine(id, medicineId, price);
            return new ResponseEntity<>("Successfully added medicine!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/{id}/changeprice/{medicineId}/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<?> changePrice(@PathVariable("id") Long id, @PathVariable("medicineId") Long medicineId, @PathVariable("price") int price) {
        try {
            priceListService.changePrice(id, medicineId, price);
            return new ResponseEntity<>("Successfully changed price of medicine!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}/removemedicine/{medicineId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<?> removeMedicine(@PathVariable("id") Long id, @PathVariable("medicineId") Long medicineId) {
        try {
            priceListService.removeMedicine(id, medicineId);
            return new ResponseEntity<>("Successfully removed medicine!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private PriceListDTO convertToDto(PriceList priceList) {
        return modelMapper.map(priceList, PriceListDTO.class);
    }

}
