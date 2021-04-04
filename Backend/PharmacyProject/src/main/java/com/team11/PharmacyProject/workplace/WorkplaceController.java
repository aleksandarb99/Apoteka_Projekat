package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.dto.WorkplaceDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/workplace")
public class WorkplaceController {


    @Autowired
    WorkplaceServise workplaceServise;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "dermatologists/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkplaceDTO>> getDermatologistWorkplacesByPharmacyId(@PathVariable("id") Long id){
        List<Workplace> workplaceList = workplaceServise.getDermatologistWorkplacesByPharmacyId(id);

        List<WorkplaceDTO> workplaceDTOList = workplaceList.stream().map(m -> modelMapper.map(m, WorkplaceDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(workplaceDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkplaceDTO>> getWorkplacesByPharmacyId(@PathVariable("id") Long id){
        List<Workplace> workplaceList = workplaceServise.getWorkplacesByPharmacyId(id);

        List<WorkplaceDTO> workplaceDTOList = workplaceList.stream().map(m -> modelMapper.map(m, WorkplaceDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(workplaceDTOList, HttpStatus.OK);
    }
}
