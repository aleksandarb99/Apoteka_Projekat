package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.advertisement.Advertisement;
import com.team11.PharmacyProject.advertisement.AdvertismentService;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.dto.medicine.MedicineTherapyDTO;
import com.team11.PharmacyProject.dto.pharmacy.*;
import com.team11.PharmacyProject.dto.rating.RatingGetEntitiesDTO;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pharmacy")
public class PharmacyController {

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    AdvertismentService advertismentService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/report/{id}/{period}/{duration}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Map<String, Double>> getInfoForReport(@PathVariable("period") String period, @PathVariable("id") Long pharmacyId, @PathVariable("duration") int duration) {
        Map<String, Double> data = pharmacyService.getInfoForReport(period, pharmacyId, duration);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPharmacyById(@PathVariable("id") Long id) {
        Pharmacy pharmacy = pharmacyService.getPharmacyByIdAndPriceList(id);

        if (pharmacy == null) {
            return new ResponseEntity<>("Pharmacy with id " + id + " does not exist!", HttpStatus.NOT_FOUND);
        }

        PharmacyDTO dto = convertToDto(pharmacy);
        dto.setPriceListId(pharmacy.getPriceList().getId());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/getpharmacyidbyadmin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<?> getPharmacyIdByAdminId(@PathVariable("id") Long id) {
        Pharmacy pharmacy = pharmacyService.getPharmacyIdByAdminId(id);

        if (pharmacy == null) {
            return new ResponseEntity<>("Pharmacy is not found!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(pharmacy.getId(), HttpStatus.OK);
    }

    @GetMapping(value = "/subscribed/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<PharmacySubscribedDTO>> getSubscribedPharmaciesByPatientId(@PathVariable("id") Long id) {
        List<PharmacySubscribedDTO> pharmacyCrudDTOs = pharmacyService.getSubscribedPharmaciesByPatientId(id).stream().map(p -> modelMapper.map(p, PharmacySubscribedDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(pharmacyCrudDTOs, HttpStatus.OK);
    }

    @PostMapping(value = "/{pharmacyId}/subscribe/{patientId}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<String> subscribe(@PathVariable("pharmacyId") long pharmacyId, @PathVariable("patientId") long patientId) {
        boolean b = pharmacyService.subscribe(pharmacyId, patientId);
        if (b) {
            return new ResponseEntity<>("User successfully subscribed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. User not subscribed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/{pharmacyId}/unsubscribe/{patientId}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<String> unsubscribe(@PathVariable("pharmacyId") long pharmacyId, @PathVariable("patientId") long patientId) {
        boolean b = pharmacyService.unsubscribe(pharmacyId, patientId);
        if (b) {
            return new ResponseEntity<>("User successfully unsubscribed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. User not unsubscribed", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{pharmacyId}/subscribe/{patientId}")
    public ResponseEntity<Boolean> isSubscribed(@PathVariable("pharmacyId") long pharmacyId, @PathVariable("patientId") long patientId) {
        boolean isSubscribed = pharmacyService.isSubscribed(pharmacyId, patientId);
        return new ResponseEntity<>(isSubscribed, HttpStatus.OK);
    }

    @PostMapping(value = "/e-recipe")
    public ResponseEntity<List<PharmacyERecipeDTO>> getPharmaciesWithAllMedicine(@RequestBody @Valid ERecipeDTO eRecipeDTO,
                                                                                 @RequestParam(value = "sort", required = false) String criteria,
                                                                                 @RequestParam(value = "order", required = false) String order) {
        String dir = "ASC";
        if (criteria == null || criteria.isEmpty()) {
            criteria = "name";
        }
        if (order != null && order.equals("DESC")) {
            dir = "DESC";
        }
        List<PharmacyERecipeDTO> pharmacyERecipeDTOS = pharmacyService.getAllWithMedicineInStock(eRecipeDTO, criteria, dir);
        return new ResponseEntity<>(pharmacyERecipeDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/e-recipe/{pharmacyId}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<String> checkIfRecipeIsInPharmacy(@RequestBody @Valid ERecipeDTO eRecipeDTO, @PathVariable("pharmacyId") Long pharmacyId) {
        try {
            pharmacyService.checkIfRecipeIsInPharmacy(eRecipeDTO, pharmacyId);
            return new ResponseEntity<>("E-Recipe is in pharmacy", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all/free-pharmacists/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> getPharmaciesByFreePharmacists(Pageable pageable, @RequestParam(value = "date", required = false) long date) {

        try {
            List<Pharmacy> pharmacies = pharmacyService.getPharmaciesByFreePharmacists(date, pageable.getSort());
            List<PharmacyConsultationDTO> retVal = new ArrayList<>();

            for (Pharmacy p : pharmacies) {
                retVal.add(new PharmacyConsultationDTO(p));
            }

            return new ResponseEntity<>(retVal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/medicine/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyCertainMedicineDTO>> getPharmaciesByMedicineId(@PathVariable("id") Long id) {
        List<Pharmacy> pharmacies = pharmacyService.getPharmaciesByMedicineId(id);

        if (pharmacies.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<PharmacyCertainMedicineDTO> dtos = new ArrayList<>();
        for (Pharmacy p : pharmacies) {
            PharmacyCertainMedicineDTO pcm = new PharmacyCertainMedicineDTO(p);
            List<Advertisement> sales = advertismentService.findAllSalesWithDate(pcm.getId(), id, System.currentTimeMillis());
            if (sales != null) {
                pcm.setPriceWithDiscout(sales.get(0).getDiscountPercent());
            }
            dtos.add(pcm);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertPharmacy(@Valid @RequestBody PharmacyCrudDTO pharmacyCrudDTO) {
        Pharmacy pharmacy = convertCrudDTOToEntity(pharmacyCrudDTO);
        if (pharmacyService.insertPharmacy(pharmacy)) {
            return new ResponseEntity<>("Pharmacy added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePharmacy(@PathVariable("id") long id) {
        if (pharmacyService.delete(id)) {
            return new ResponseEntity<>("Pharmacy deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<String> updatePharmacy(@PathVariable("id") long id, @Valid @RequestBody PharmacyDTO pharmacyDTO) {
        Pharmacy pharmacy = convertToEntity(pharmacyDTO);
        try {
            pharmacyService.update(id, pharmacy);
            return new ResponseEntity<>("Pharmacy updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/crud", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyCrudDTO>> getAllPharmaciesDTO() {
        List<PharmacyCrudDTO> pharmacyCrudDTOs = pharmacyService.getAll().stream().map(this::convertToCrudDTO).collect(Collectors.toList());
        return new ResponseEntity<>(pharmacyCrudDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {
        List<PharmacyDTO> list = new ArrayList<>();
        for (Pharmacy p : pharmacyService.getAll()) {
            list.add(convertToDto(p));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyAllDTO>> searchPharmaciesByNameOrCity
            (@Valid @RequestParam(value = "searchValue", required = false) String searchValue) throws Exception {

        // TODO vidi kako cemo hanladati errore, da li moram rucno proverati da li je prsledjeni atribut prazan string ili predugacak, null, itd.

        List<Pharmacy> pharmacyResult = pharmacyService.searchPharmaciesByNameOrCity(searchValue);
        List<PharmacyAllDTO> pharmacyDTOS = new ArrayList<>();
        for (Pharmacy p : pharmacyResult) {
            pharmacyDTOS.add(convertToAllDto(p));
        }
        return new ResponseEntity<>(pharmacyDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/getMedicineFromPharmWithoutAllergies", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PHARMACIST', 'DERMATOLOGIST')")
    public ResponseEntity<List<MedicineTherapyDTO>> getPharmacyMedicine
            (@RequestParam(value = "pharm_id", required = true) Long pharmID,
             @RequestParam(value = "patient_id", required = true) Long patientID) {
        Pharmacy pharm = pharmacyService.getPharmacyWithMedicineNoAllergies(pharmID, patientID);
        List<MedicineTherapyDTO> medDto = new ArrayList<>();
        try {
            for (MedicineItem m : pharm.getPriceList().getMedicineItems()) {
                medDto.add(new MedicineTherapyDTO(m));
            }
            return new ResponseEntity<>(medDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getAlternativeFromPharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PHARMACIST', 'DERMATOLOGIST')")
    public ResponseEntity<List<MedicineTherapyDTO>> getPharmacyAlternativeMedicine
            (@RequestParam(value = "worker_id", required = true) Long workerID,
             @RequestParam(value = "pharm_id", required = true) Long pharmID,
             @RequestParam(value = "patient_id", required = true) Long patientID,
             @RequestParam(value = "medicine_item_id", required = true) Long medicineItemID,
             @RequestParam(value = "medicine_id", required = true) Long medicineID) {
        Pharmacy pharm = pharmacyService.getPharmacyWithAlternativeForMedicineNoAllergies(pharmID, patientID, medicineID);
        if (pharm == null) {
            return new ResponseEntity<>(new ArrayList<MedicineTherapyDTO>(), HttpStatus.OK);
        }
        List<MedicineTherapyDTO> medDto = new ArrayList<>();
        try {
            for (MedicineItem m : pharm.getPriceList().getMedicineItems()) {
                medDto.add(new MedicineTherapyDTO(m));
            }

            boolean creatingInquiry = pharmacyService.createInquiry(workerID, medicineItemID, pharm);
            if (!creatingInquiry) {
                System.out.println("Inquiry not created!");
            }
            return new ResponseEntity<>(medDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all-pharmacies/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> getPharmaciesByPatientId(@PathVariable("id") Long id) {

        List<Pharmacy> pharmacies = pharmacyService.getPharmaciesByPatientId(id);

        if (pharmacies == null) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }

        List<RatingGetEntitiesDTO> retVal = pharmacies.stream().map(RatingGetEntitiesDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    private PharmacyDTO convertToDto(Pharmacy pharmacy) {
        return modelMapper.map(pharmacy, PharmacyDTO.class);
    }

    private PharmacyAllDTO convertToAllDto(Pharmacy pharmacy) {
        return modelMapper.map(pharmacy, PharmacyAllDTO.class);
    }

    private Pharmacy convertToEntity(PharmacyDTO pharmacyDto) {
        return modelMapper.map(pharmacyDto, Pharmacy.class);
    }

    private PharmacyCrudDTO convertToCrudDTO(Pharmacy pharmacy) {
        return modelMapper.map(pharmacy, PharmacyCrudDTO.class);
    }

    private Pharmacy convertCrudDTOToEntity(PharmacyCrudDTO pharmacyCrudDto) {
        return modelMapper.map(pharmacyCrudDto, Pharmacy.class);
    }
}
