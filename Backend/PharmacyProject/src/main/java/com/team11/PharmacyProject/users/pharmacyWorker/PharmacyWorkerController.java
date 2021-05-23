package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.dto.appointment.AppointmentCalendarDTO;
import com.team11.PharmacyProject.dto.appointment.AppointmentDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyConsultationDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyWorkerDTO;
import com.team11.PharmacyProject.dto.pharmacyWorker.PharmacyWorkerFreePharmacistDTO;
import com.team11.PharmacyProject.dto.pharmacyWorker.RequestForWorkerDTO;
import com.team11.PharmacyProject.dto.worker.HolidayStartEndDTO;
import com.team11.PharmacyProject.dto.worker.WorktimeDTO;
import com.team11.PharmacyProject.dto.rating.RatingGetEntitiesDTO;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.requestForHoliday.RequestForHoliday;
import com.team11.PharmacyProject.requestForHoliday.RequestForHolidayService;
import com.team11.PharmacyProject.workplace.Workplace;
import com.team11.PharmacyProject.workplace.WorkplaceController;
import com.team11.PharmacyProject.workplace.WorkplaceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/workers")
public class PharmacyWorkerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PharmacyWorkerService pharmacyWorkerService;

    @Autowired
    WorkplaceService workplaceService;

    @Autowired
    RequestForHolidayService requestForHolidayService;

    @GetMapping(value = "/calendarAppointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentCalendarDTO>> getWorkerCalendar(@PathVariable("id") Long id){
        //todo dodati mozda i radno vreme u kalendar
        PharmacyWorker worker = pharmacyWorkerService.getWorkerForCalendar(id);

        if (worker == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<AppointmentCalendarDTO> appts = new ArrayList<>();
        for (Appointment appointment : worker.getAppointmentList()) {
            appts.add(new AppointmentCalendarDTO(appointment));
        }

        return new ResponseEntity<List<AppointmentCalendarDTO>>(appts, HttpStatus.OK);
    }

    @GetMapping(value = "/all/free-pharmacists/pharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPharmaciesByFreePharmacists(Pageable pageable, @RequestParam(value = "date", required = false) long date, @RequestParam(value = "id", required = false) Long id) {

        try {

            List<PharmacyWorker> workers = pharmacyWorkerService.getFreePharmacistsByPharmacyIdAndDate(id, date, pageable.getSort());
            List<PharmacyWorkerFreePharmacistDTO> retVal = new ArrayList<>();

            for(PharmacyWorker pw : workers) {
                retVal.add(new PharmacyWorkerFreePharmacistDTO(pw));
            }

            return new ResponseEntity<>(retVal, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all-dermatologists/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDermatologistsByPatientId(@PathVariable("id") Long id) {

        List<PharmacyWorker> workers = pharmacyWorkerService.getDermatologistsByPatientId(id);

        if (workers == null) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }

        List<RatingGetEntitiesDTO> retVal = workers.stream().map(RatingGetEntitiesDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(value = "/all-pharmacists/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPharmacistsByPatientId(@PathVariable("id") Long id) {

        List<PharmacyWorker> workers = pharmacyWorkerService.getPharmacistsByPatientId(id);

        if (workers == null) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }

        List<RatingGetEntitiesDTO> retVal = workers.stream().map(RatingGetEntitiesDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping(value = "/notexistingworkplacebypharmacyid/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotWorkingWorkersByPharmacyId(@PathVariable("pharmacyId") Long pharmacyId, @RequestBody RequestForWorkerDTO dto) {
        try {
            WorkplaceController.checkDto(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        List<PharmacyWorker> workers = pharmacyWorkerService.getNotWorkingWorkersByPharmacyId(pharmacyId, dto);

        List<PharmacyWorkerDTO> retVal = new ArrayList<>();
        for(PharmacyWorker pw : workers) {
            retVal.add(convertToDto(pw));
        }

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping(value = "/getWorkTimeForReport", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorktimeDTO> getWorkTimeForPharmacist(@RequestParam("workerID") Long workerID) {
        Workplace wp = workplaceService.getWorkplaceOfPharmacist(workerID);
        if (wp == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        WorktimeDTO worktimeDTO = new WorktimeDTO();
        worktimeDTO.setWorkDayList(wp.getWorkDays());

        List<RequestForHoliday> holidays = requestForHolidayService.getRequestForHolidayAcceptedOrPendingInFuture(workerID);
        if (holidays != null && !holidays.isEmpty()){
            List<HolidayStartEndDTO> holidayStartEndDTOS = new ArrayList<>(holidays.size());
            for (RequestForHoliday req : holidays){
                holidayStartEndDTOS.add(new HolidayStartEndDTO(req));
            }
            worktimeDTO.setHolidays(holidayStartEndDTOS);
        }
        return new ResponseEntity<>(worktimeDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/getWorkTimeForReportDerm", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorktimeDTO> getWorkTimeForDermatologist(@RequestParam("workerID") Long workerID,
                                                                   @RequestParam("pharmacyID") Long pharmID) {
        Workplace wp = workplaceService.getWorkplaceOfDermatologist(workerID, pharmID);
        // todo ovo se moze refaktorisati, kopiranje koda
        if (wp == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        WorktimeDTO worktimeDTO = new WorktimeDTO();
        worktimeDTO.setWorkDayList(wp.getWorkDays());

        List<RequestForHoliday> holidays = requestForHolidayService.getRequestForHolidayAcceptedOrPendingInFuture(workerID);
        if (holidays != null && !holidays.isEmpty()){
            List<HolidayStartEndDTO> holidayStartEndDTOS = new ArrayList<>(holidays.size());
            for (RequestForHoliday req : holidays){
                holidayStartEndDTOS.add(new HolidayStartEndDTO(req));
            }
            worktimeDTO.setHolidays(holidayStartEndDTOS);
        }
        return new ResponseEntity<>(worktimeDTO, HttpStatus.OK);
    }

    private PharmacyWorkerDTO convertToDto(PharmacyWorker worker) {
        return modelMapper.map(worker, PharmacyWorkerDTO.class);

    }


}
