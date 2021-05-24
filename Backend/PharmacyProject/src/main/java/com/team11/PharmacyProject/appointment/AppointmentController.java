package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.dto.appointment.*;
import com.team11.PharmacyProject.dto.therapyPrescription.TherapyDTO;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.enums.AppointmentType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/appointment")
public class AppointmentController {


    @Autowired
    AppointmentService appointmentServiceImpl;

    @Autowired
    EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/report/{id}/{period}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Map<String, Integer>> getInfoForReport(@PathVariable("period") String period, @PathVariable("id") Long pharmacyId) {
        Map<String, Integer> data = appointmentServiceImpl.getInfoForReport(period, pharmacyId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/{idP}/{idD}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<String> addAppointment(@PathVariable("idP") Long pharmacyId, @PathVariable("idD") Long dId, @Valid @RequestBody AppointmentDTORequest dto) {
        Appointment a = convertToEntity(dto);
        try {
            appointmentServiceImpl.insertAppointment(a, pharmacyId, dId);
            return new ResponseEntity<>("Appointment added successfully", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all/bydermatologistid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<?> getAllAppointmentsByPharmacyId(@PathVariable("id") Long id, @RequestParam(name = "date") Long date) {
        List<AppointmentDTO> appointmentsDTO = null;
        try {
            appointmentsDTO = appointmentServiceImpl.getAllAppointmentsByPharmacyId(id, date).stream().map(m -> modelMapper.map(m, AppointmentDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping(value = "/history/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<AppointmentPatientInsightDTO>> getFinishedConsultationsByPatientId(Pageable pageable, @PathVariable("id") Long id) {
        List<AppointmentPatientInsightDTO> appointmentsDTO = appointmentServiceImpl.getFinishedConsultationsByPatientId(id, pageable.getSort());
        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/upcoming/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<AppointmentPatientInsightDTO>> getUpcomingConsultationsByPatientId(Pageable pageable, @PathVariable("id") Long id) {
        List<AppointmentPatientInsightDTO> appointmentsDTO = appointmentServiceImpl.getUpcomingConsultationsByPatientId(id, pageable.getSort());
        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/checkups/history/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<AppointmentPatientInsightDTO>> getFinishedCheckupsByPatientId(Pageable pageable, @PathVariable("id") Long id) {
        List<AppointmentPatientInsightDTO> appointmentsDTO = appointmentServiceImpl.getFinishedCheckupsByPatientId(id, pageable.getSort());
        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/checkups/upcoming/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<AppointmentPatientInsightDTO>> getUpcomingCheckupsByPatientId(Pageable pageable, @PathVariable("id") Long id) {
        List<AppointmentPatientInsightDTO> appointmentsDTO = appointmentServiceImpl.getUpcomingCheckupsByPatientId(id, pageable.getSort());
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
    public ResponseEntity<List<AppointmentReportDTO>> getNextAppointments(
            @RequestParam(name = "patient") String email, @RequestParam(name = "worker") Long workerId) {

        List<Appointment> app = appointmentServiceImpl.getNextAppointments(email, workerId);
        if (!app.isEmpty()) {
            List<AppointmentReportDTO> dtos = new ArrayList<>();
            for (Appointment apo : app) {
                dtos.add(new AppointmentReportDTO(apo)); //todo pazi lazy loading farmacije
            }
            return new ResponseEntity<List<AppointmentReportDTO>>(dtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<AppointmentReportDTO>>(HttpStatus.NOT_FOUND);
        }
    }

    private Appointment convertToEntity(AppointmentDTORequest dto) {
        return modelMapper.map(dto, Appointment.class);
    }

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

    @PutMapping(value = "/cancel-consultation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<String> cancelConsultation (@PathVariable(value="id") Long id)
    {
        try {
            appointmentServiceImpl.cancelConsultation(id);
        }catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("The consultation is canceled successfully!", HttpStatus.OK);
    }

    @PutMapping(value = "/cancel-checkup/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<String> cancelCheckup (@PathVariable(value="id") Long id)
    {
        try {
            appointmentServiceImpl.cancelCheckup(id);
        }catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("The checkup is canceled successfully!", HttpStatus.OK);
    }

    @PostMapping(value = "/reserve/{idA}/patient/{idP}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<String> reserveCheckupForPatient(@PathVariable("idP") Long patientId, @PathVariable("idA") Long appId) {

        try {
            AppointmentReservationDTO dto = appointmentServiceImpl.reserveCheckupForPatient(appId, patientId);
            emailService.notifyPatientAboutReservedAppointment(dto, "Pregled");

            return new ResponseEntity<>("Successfully reserved the checkup!", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/reserve-consultation/pharmacy/{idPh}/pharmacist/{idW}/patient/{idPa}/date/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<String> reserveConsultationForPatient(@PathVariable("idPa") Long patientId, @PathVariable("idW") Long workerId, @PathVariable("idPh") Long pharmacyId, @PathVariable("date") Long date) {

        try {
            AppointmentReservationDTO dto = appointmentServiceImpl.reserveConsultationForPatient(workerId, patientId, pharmacyId, date);
            emailService.notifyPatientAboutReservedAppointment(dto, "Savetovanje");

            return new ResponseEntity<>("Successfully reserved the consultation!", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/finalizeAppointment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> finalizeAppointment (@RequestBody TherapyDTO therapyDTO)
    {
        //TODO trebace kasnije - ako je neko u medjuvremenu uzeo te lekove, javi frontu! i resetuj terapiju
        boolean result = appointmentServiceImpl.finalizeAppointment(therapyDTO.getApptId(), therapyDTO.getMedicineList(), therapyDTO.getInfo());
        if (result)
            return new ResponseEntity<>("Finalized appointment!", HttpStatus.OK);
        return new ResponseEntity<>("Failed to add therapy!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/getApptForReport", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentReportDTO> getAppointmentForReport (@RequestParam(value="id") Long id)
    {
        Appointment appt = appointmentServiceImpl.getAppointmentForReport(id);
        if(appt != null){
            AppointmentReportDTO dto = new AppointmentReportDTO(appt);
            return new ResponseEntity<AppointmentReportDTO>(dto, HttpStatus.OK);
        }else{
            return new ResponseEntity<AppointmentReportDTO>(new AppointmentReportDTO(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/appointmentsOnThatDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentTimeRangeDTO>> getAppointmentsOnDate(@RequestParam("workerID") Long workerID,
                                                                      @RequestParam("patientID") Long patientID,
                                                                      @RequestParam("date") Long date)
    {
        List<AppointmentTimeRangeDTO> appointmentsDTO = null;
        try {
            appointmentsDTO = appointmentServiceImpl.getAppointmentsOfPatientWorkerOnDate(workerID, patientID, date)
                    .stream().map(m -> modelMapper.map(m, AppointmentTimeRangeDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
        } catch (Exception e){
        e.printStackTrace();
        }
        return new ResponseEntity<>(appointmentsDTO, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/scheduleAppointment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> scheduleAppointment(@RequestParam("patientID") Long patientId,
                                                      @RequestParam("workerID") Long workerId,
                                                      @RequestParam("pharmacyID") Long pharmacyId,
                                                      @RequestParam("date") Long date,
                                                      @RequestParam("duration") int duration)
    {
        if (duration < 0){
            return new ResponseEntity<>("Duration less than 0!", HttpStatus.BAD_REQUEST);
        }
        long endTime = date + TimeUnit.MINUTES.toMillis(duration); // appt end time
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTimeInMillis(date);
        c2.setTimeInMillis(endTime);
        if (!(c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) &&
                c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))){
            return new ResponseEntity<>("Appointment has to start and end on the same date!", HttpStatus.BAD_REQUEST);
        }

        try{
            Appointment appt = appointmentServiceImpl.scheduleAppointmentInRange(workerId, patientId, pharmacyId, date, endTime, duration);
            if (appt != null){
                emailService.notifyPatientAboutReservedAppointment(new AppointmentReservationDTO(appt),
                        (appt.getAppointmentType() == AppointmentType.CHECKUP) ? "Pregled" : "Savetovanje");
                return new ResponseEntity<>("All good", HttpStatus.OK);
            }

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Invalid appt date!", HttpStatus.BAD_REQUEST);
    }

    @Scheduled(cron = "${greeting.cron}")
    public void endAppointments() {
        appointmentServiceImpl.finishUnfinishedAppointments();
    }
}
