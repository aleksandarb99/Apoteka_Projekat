package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.dto.workplace.WorkplaceDTO;
import com.team11.PharmacyProject.dto.workplace.WorkplaceDTOWithWorkdays;
import com.team11.PharmacyProject.dto.workplace.WorkplaceForWorkerProfileDTO;
import org.hibernate.jdbc.Work;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkplaceDTO>> getWorkplacesByPharmacyId(@PathVariable("id") Long id) {
        List<Workplace> workplaceList = workplaceServiseImpl.getWorkplacesByPharmacyId(id);

        List<WorkplaceDTO> workplaceDTOList = workplaceList.stream().map(m -> modelMapper.map(m, WorkplaceDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(workplaceDTOList, HttpStatus.OK);
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
