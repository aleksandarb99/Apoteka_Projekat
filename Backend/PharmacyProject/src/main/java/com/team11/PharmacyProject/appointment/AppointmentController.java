package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.dto.appointment.AppointmentDTO;
import com.team11.PharmacyProject.dto.appointment.AppointmentDTORequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/appointment")
public class AppointmentController {


    @Autowired
    AppointmentServiceImpl appointmentServiceImpl;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/{idP}/{idD}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addAppointment(@PathVariable("idP") Long pharmacyId, @PathVariable("idD") Long dId, @Valid @RequestBody AppointmentDTORequest dto) {

        Appointment a = convertToEntity(dto);
        if (appointmentServiceImpl.insertAppointment(a, pharmacyId, dId)) {
            return new ResponseEntity<>("Appointment added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all/bydermatologistid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsByPharmacyId(@PathVariable("id") Long id, @RequestParam(name = "date") Long date) {
        List<AppointmentDTO> appointmentsDTO = appointmentServiceImpl.getAllAppointmentsByPharmacyId(id, date).stream().map(m -> modelMapper.map(m, AppointmentDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> getFreeAppointmentsByPharmacyId(@PathVariable("id") Long id) {
        List<AppointmentDTO> appointmentsDTO = appointmentServiceImpl.getFreeAppointmentsByPharmacyId(id).stream().map(m -> modelMapper.map(m, AppointmentDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/byPatWorkerFirst", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDTO> getNextAppointment(
            @RequestParam(name = "patient") String email, @RequestParam(name = "worker") Long workerId) {

        Appointment app = appointmentServiceImpl.getNextAppointment(email, workerId);
        if (app != null) {
            AppointmentDTO dto = modelMapper.map(app, AppointmentDTO.class);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/byPatWorker", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> getNextAppointments(
            @RequestParam(name = "patient") String email, @RequestParam(name = "worker") Long workerId) {

        List<Appointment> app = appointmentServiceImpl.getNextAppointments(email, workerId);
        if (!app.isEmpty()) {
            List<AppointmentDTO> dtos = new ArrayList<>();
            for (Appointment apo : app) {
                dtos.add(modelMapper.map(apo, AppointmentDTO.class));
            }
            return new ResponseEntity<List<AppointmentDTO>>(dtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<AppointmentDTO>>(HttpStatus.NOT_FOUND);
        }
    }

    private Appointment convertToEntity(AppointmentDTORequest dto) {
        return modelMapper.map(dto, Appointment.class);
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
