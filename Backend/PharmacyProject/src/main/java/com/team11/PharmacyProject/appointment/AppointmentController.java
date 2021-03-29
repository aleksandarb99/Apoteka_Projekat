package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.dto.AppointmentDTO;
import com.team11.PharmacyProject.dto.MedicineDTO;
import com.team11.PharmacyProject.dto.PharmacyDTO;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import com.team11.PharmacyProject.users.patient.PatientDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    @PostMapping(value = "/byPatWorkerFirst", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDTO> getNextAppointment(
            @RequestParam(name="patient") String email,  @RequestParam(name="worker") Long workerId){

        Appointment app = appointmentService.getNextAppointment(email, workerId);
        if (app != null) {
            AppointmentDTO dto = modelMapper.map(app, AppointmentDTO.class);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/byPatWorker", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> getNextAppointments(
            @RequestParam(name="patient") String email,  @RequestParam(name="worker") Long workerId){

        List<Appointment> app = appointmentService.getNextAppointments(email, workerId);
        if (!app.isEmpty()) {
            List<AppointmentDTO> dtos = new ArrayList<>();
            for(Appointment apo : app){
                dtos.add(modelMapper.map(apo, AppointmentDTO.class));
            }
            return new ResponseEntity<List<AppointmentDTO>>(dtos, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<AppointmentDTO>>(HttpStatus.NOT_FOUND);
        }
    }
//    @PostMapping(value = "/byPatWorker", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<AppointmentDTO> getNextAppointment(
//            @RequestBody HashMap<String, Long> mapica){
//
////        Appointment app = appointmentService.getNextAppointment(patId, workerID);
////        if (app != null) {
////            AppointmentDTO dto = modelMapper.map(app, AppointmentDTO.class);
////            return new ResponseEntity<>(dto, HttpStatus.OK);
////        }else{
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

}
