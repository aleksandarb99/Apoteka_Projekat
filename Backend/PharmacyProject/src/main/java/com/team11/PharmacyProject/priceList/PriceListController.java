package com.team11.PharmacyProject.priceList;

import com.team11.PharmacyProject.dto.medicine.MedicineItemDTO;
import com.team11.PharmacyProject.dto.pricelist.PriceListDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pricelist")
public class PriceListController {

    @Autowired
    PriceListService priceListService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceListDTO> getPriceListById(@PathVariable("id") Long id) {
        PriceList priceList = priceListService.findById(id);

        if (priceList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        priceListService.deleteDuplicates(priceList);

        PriceListDTO dto = convertToDto(priceList);
        for (MedicineItemDTO item : dto.getMedicineItems()) {
            item.setPrice(priceListService.getMedicineItemPrice(item.getId()));
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/addmedicine/{medicineId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceListDTO> insertMedicine(@PathVariable("id") Long id, @PathVariable("medicineId") Long medicineId) {
        PriceList priceList = priceListService.insertMedicine(id, medicineId);

        if (priceList != null) {
            PriceListDTO dto = convertToDto(priceList);

            for (MedicineItemDTO item : dto.getMedicineItems()) {
                item.setPrice(priceListService.getMedicineItemPrice(item.getId()));
            }
            return new ResponseEntity<PriceListDTO>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<PriceListDTO>((PriceListDTO) null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}/removemedicine/{medicineId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceListDTO> removeMedicine(@PathVariable("id") Long id, @PathVariable("medicineId") Long medicineId) {
        PriceList priceList = priceListService.removeMedicine(id, medicineId);

        if (priceList != null) {
            PriceListDTO dto = convertToDto(priceList);

            for (MedicineItemDTO item : dto.getMedicineItems()) {
                item.setPrice(priceListService.getMedicineItemPrice(item.getId()));
            }
            return new ResponseEntity<PriceListDTO>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<PriceListDTO>((PriceListDTO) null, HttpStatus.BAD_REQUEST);
    }

    private PriceListDTO convertToDto(PriceList priceList) {
        return modelMapper.map(priceList, PriceListDTO.class);
    }

}
