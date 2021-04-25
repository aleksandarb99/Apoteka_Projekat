package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.dto.appointment.*;
import com.team11.PharmacyProject.email.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/appointment")
public class AppointmentController {


    @Autowired
    AppointmentServiceImpl appointmentServiceImpl;

    @Autowired
    EmailService emailService;

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

    @GetMapping(value = "/bypharmacyid/{id}/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> getFreeAppointmentsByPharmacyIdSorting(Pageable pageable, @PathVariable("id") Long id) {
        Sort sorter = pageable.getSort();
        List<AppointmentDTO> appointmentsDTO = appointmentServiceImpl.getFreeAppointmentsByPharmacyId(id, sorter).stream().map(m -> modelMapper.map(m, AppointmentDTO.class)).collect(Collectors.toList());
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

    @GetMapping(value = "/workers_upcoming", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentCalendarDTO>> getUpcommingAppointments(
            @RequestParam(value="id") Long id, @RequestParam(value="page") int page, @RequestParam(value="size") int size)
    {
        List<Appointment> appts = appointmentServiceImpl.getUpcomingAppointmentsForWorker(id, page, size);
        if(appts == null){
            return new ResponseEntity<List<AppointmentCalendarDTO>>(HttpStatus.NOT_FOUND);
        }else if (appts.isEmpty()){
            return new ResponseEntity<List<AppointmentCalendarDTO>>(HttpStatus.NOT_FOUND);
        }

        List<AppointmentCalendarDTO> dtos = new ArrayList<>(appts.size());
        for (Appointment appointment : appts) {
            dtos.add(new AppointmentCalendarDTO(appointment));
        }

        return new ResponseEntity<List<AppointmentCalendarDTO>>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/start_appointment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> startAppointment (@RequestParam(value="id") Long id)
    {
        boolean started = appointmentServiceImpl.startAppointment(id);
        if(started){
            return new ResponseEntity<String>("Started the appointment", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Couldn't start the appointment",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/cancel_appointment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelAppointment (@RequestParam(value="id") Long id)
    {
        boolean started = appointmentServiceImpl.cancelAppointment(id);
        if(started){
            return new ResponseEntity<String>("Cancelled the appointment", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Couldn't cancel the appointment",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/reserve/{idA}/patient/{idP}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reserveAppointmentForPatient(@PathVariable("idP") Long patientId, @PathVariable("idA") Long appId) {

        AppointmentCheckupReservationDTO dto = appointmentServiceImpl.reserveAppointmentForPatient(appId, patientId);
        if (dto != null) {
            try {
                emailService.notifyPatientAboutReservedCheckup(dto);
            } catch (Exception e) {
                e.printStackTrace();        // Verovatno moze puci zbog nedostatka interneta, ili ako nije dozvoljeno za manje bezbedne aplikacije itd.
            }
            return new ResponseEntity<>("reserved", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failed", HttpStatus.OK);
        }
    }

    @PostMapping(value = "/reserve-consultation/pharmacy/{idPh}/pharmacist/{idW}/patient/{idPa}/date/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reserveConsultationForPatient(@PathVariable("idPa") Long patientId, @PathVariable("idW") Long workerId, @PathVariable("idPh") Long pharmacyId, @PathVariable("date") Long date) {

        AppointmentConsultationReservationDTO dto = appointmentServiceImpl.reserveConsultationForPatient(workerId, patientId, pharmacyId, date);
        if (dto != null) {
            try {
                emailService.notifyPatientAboutReservedConsultation(dto);
            } catch (Exception e) {
                e.printStackTrace();        // Verovatno moze puci zbog nedostatka interneta, ili ako nije dozvoljeno za manje bezbedne aplikacije itd.
            }
            return new ResponseEntity<>("reserved", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failed", HttpStatus.OK);
        }
    }

}
