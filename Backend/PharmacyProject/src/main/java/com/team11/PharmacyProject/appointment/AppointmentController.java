package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.dto.AppointmentDTO;
import com.team11.PharmacyProject.dto.MedicineDTO;
import com.team11.PharmacyProject.dto.PharmacyDTO;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
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
@RequestMapping("api/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> getFreeAppointmentsByPharmacyId(@PathVariable("id") Long id){
        List<AppointmentDTO> appointmentsDTO = appointmentService.getFreeAppointmentsByPharmacyId(id).stream().map(m -> modelMapper.map(m, AppointmentDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

}
