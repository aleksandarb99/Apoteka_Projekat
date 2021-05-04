package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.dto.pharmacyWorker.RequestForWorkerDTO;
import com.team11.PharmacyProject.dto.workplace.WorkplaceDTO;
import com.team11.PharmacyProject.dto.workplace.WorkplaceDTOWithWorkdays;
import com.team11.PharmacyProject.dto.workplace.WorkplaceForWorkerProfileDTO;
import org.hibernate.jdbc.Work;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/workplace")
public class WorkplaceController {


    @Autowired
    WorkplaceServiseImpl workplaceServiseImpl;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "dermatologists/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkplaceDTOWithWorkdays>> getDermatologistWorkplacesByPharmacyId(@PathVariable("id") Long id) {
        List<Workplace> workplaceList = workplaceServiseImpl.getDermatologistWorkplacesByPharmacyId(id);

        List<WorkplaceDTOWithWorkdays> workplaceDTOList = workplaceList.stream().map(m -> modelMapper.map(m, WorkplaceDTOWithWorkdays.class)).collect(Collectors.toList());
        return new ResponseEntity<>(workplaceDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "/addworker/bypharmacyid/{id}/{workerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addWorker(@PathVariable("id") Long id,@PathVariable("workerId") Long workerId, @RequestBody RequestForWorkerDTO dto) {
        if (checkDto(dto)) return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);

        boolean flag = workplaceServiseImpl.addWorker(id, workerId, dto);

        if(!flag){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully added", HttpStatus.OK);
    }

    @DeleteMapping(value = "/removeworker/bypharmacyid/{id}/{workerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeWorker(@PathVariable("id") Long id,@PathVariable("workerId") Long workerId) {
        boolean flag = workplaceServiseImpl.removeWorker(id, workerId);

        if(!flag){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully removed", HttpStatus.OK);
    }

    public static boolean checkDto(@RequestBody RequestForWorkerDTO dto) {
        if(dto.getStartHour() >= dto.getEndHour()){
            return true;
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
        if(!flag){
            return true;
        }
        return false;
    }

    @GetMapping(value = "/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkplaceDTO>> getWorkplacesByPharmacyId(@PathVariable("id") Long id) {
        List<Workplace> workplaceList = workplaceServiseImpl.getWorkplacesByPharmacyId(id);

        List<WorkplaceDTO> workplaceDTOList = workplaceList.stream().map(m -> modelMapper.map(m, WorkplaceDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(workplaceDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/pharmacies/byworkerid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getPharmacyNamesByWorkerId(@PathVariable("id") Long id) {
        List<String> pharmacies = workplaceServiseImpl.getPharmacyNamesByWorkerId(id);

        return new ResponseEntity<>(pharmacies, HttpStatus.OK);
    }

    @GetMapping(value = "/dermatologist/get_workplaces/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkplaceForWorkerProfileDTO>> getWorkplacesOfDermatologist(@PathVariable("id") Long id) {
        List<Workplace> workplaceList = workplaceServiseImpl.getWorkplacesOfDermatologist(id);
        List<WorkplaceForWorkerProfileDTO> dtos = new ArrayList<>(workplaceList.size());
        for (Workplace workplace : workplaceList){
            dtos.add(new WorkplaceForWorkerProfileDTO(workplace));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/pharmacist/get_workplace/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkplaceForWorkerProfileDTO> getWorkplaceOfPharmacist(@PathVariable("id") Long id) {
        Workplace workplace = workplaceServiseImpl.getWorkplaceOfPharmacist(id);
        if (workplace == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        WorkplaceForWorkerProfileDTO workplaceForWorkerProfileDTO = new WorkplaceForWorkerProfileDTO(workplace);
        return new ResponseEntity<>(workplaceForWorkerProfileDTO, HttpStatus.OK);
    }
}
