package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.dto.pharmacy.PharmacyAllDTO;
import com.team11.PharmacyProject.dto.pharmacyWorker.RequestForWorkerDTO;
import com.team11.PharmacyProject.dto.workplace.WorkplaceDTO;
import com.team11.PharmacyProject.dto.workplace.WorkplaceDTOWithWorkdays;
import com.team11.PharmacyProject.dto.workplace.WorkplaceForWorkerProfileDTO;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import org.hibernate.jdbc.Work;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/workplace")
public class WorkplaceController {


    @Autowired
    WorkplaceServiseImpl workplaceServiseImpl;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "dermatologists/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<List<WorkplaceDTOWithWorkdays>> getDermatologistWorkplacesByPharmacyId(@PathVariable("id") Long id) {
        List<Workplace> workplaceList = workplaceServiseImpl.getDermatologistWorkplacesByPharmacyId(id);

        List<WorkplaceDTOWithWorkdays> workplaceDTOList = workplaceList.stream().map(m -> modelMapper.map(m, WorkplaceDTOWithWorkdays.class)).collect(Collectors.toList());
        return new ResponseEntity<>(workplaceDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "/addworker/bypharmacyid/{id}/{workerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<String> addWorker(@PathVariable("id") Long id,@PathVariable("workerId") Long workerId, @RequestBody RequestForWorkerDTO dto) {
        try {
            WorkplaceController.checkDto(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            workplaceServiseImpl.addWorker(id, workerId, dto);
            return new ResponseEntity<>("Worker added successfully", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/removeworker/bypharmacyid/{id}/{workerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<String> removeWorker(@PathVariable("id") Long id,@PathVariable("workerId") Long workerId) {
        try {
            workplaceServiseImpl.removeWorker(id, workerId);
            return new ResponseEntity<>("Worker removed successfully", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkDto(@RequestBody RequestForWorkerDTO dto) {
        if(dto.getStartHour() >= dto.getEndHour()){
            throw new RuntimeException("Hours in workschedule are not correct!Start hour should be before end hour!");
        }
        boolean flag = false;
        if(dto.isEnable1()){
            flag = true;
        }else if(dto.isEnable2()){
            flag = true;
        }else if(dto.isEnable3()){
            flag = true;
        }
        else if(dto.isEnable4()){
            flag = true;
        }else if(dto.isEnable5()){
            flag = true;
        }else if(dto.isEnable6()){
            flag = true;
        }else if(dto.isEnable7()){
            flag = true;
        }
        if(!flag)
            throw new RuntimeException("You must select one day when he will work!");
    }

    @GetMapping(value = "/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkplaceDTO>> getWorkplacesByPharmacyId(@PathVariable("id") Long id) {
        List<Workplace> workplaceList = workplaceServiseImpl.getWorkplacesByPharmacyId(id);

        List<WorkplaceDTO> workplaceDTOList = workplaceList.stream().map(m -> modelMapper.map(m, WorkplaceDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(workplaceDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/search/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<List<WorkplaceDTO>> searchWorkplaces(@PathVariable("id") Long pharmacyId, @Valid @RequestParam(value = "searchValue", required = false) String searchValue) throws Exception {
        List<Workplace> workplaceList = workplaceServiseImpl.searchWorkplacesByNameOrSurnameOfWorker(searchValue, pharmacyId);

        List<WorkplaceDTO> workplaceDTOList = workplaceList.stream().map(m -> modelMapper.map(m, WorkplaceDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(workplaceDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/pharmacies/all/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Map<Long, List<String>>> getAllPharmacyNames() {
        Map<Long, List<String>> pharmacies = workplaceServiseImpl.getAllPharmacyNames();

        return new ResponseEntity<>(pharmacies, HttpStatus.OK);
    }

    @GetMapping(value = "/dermatologist/get_workplaces/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('DERMATOLOGIST')")
    public ResponseEntity<List<WorkplaceForWorkerProfileDTO>> getWorkplacesOfDermatologist(@PathVariable("id") Long id) {
        List<Workplace> workplaceList = workplaceServiseImpl.getWorkplacesOfDermatologist(id);
        List<WorkplaceForWorkerProfileDTO> dtos = new ArrayList<>(workplaceList.size());
        for (Workplace workplace : workplaceList){
            dtos.add(new WorkplaceForWorkerProfileDTO(workplace));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/pharmacist/get_workplace/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACIST')")
    public ResponseEntity<WorkplaceForWorkerProfileDTO> getWorkplaceOfPharmacist(@PathVariable("id") Long id) {
        Workplace workplace = workplaceServiseImpl.getWorkplaceOfPharmacist(id);
        if (workplace == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        WorkplaceForWorkerProfileDTO workplaceForWorkerProfileDTO = new WorkplaceForWorkerProfileDTO(workplace);
        return new ResponseEntity<>(workplaceForWorkerProfileDTO, HttpStatus.OK);
    }
}
