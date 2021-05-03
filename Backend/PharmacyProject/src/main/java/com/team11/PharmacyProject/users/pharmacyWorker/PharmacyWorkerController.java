package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.dto.appointment.AppointmentCalendarDTO;
import com.team11.PharmacyProject.dto.appointment.AppointmentDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyConsultationDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyWorkerDTO;
import com.team11.PharmacyProject.dto.pharmacyWorker.PharmacyWorkerFreePharmacistDTO;
import com.team11.PharmacyProject.dto.pharmacyWorker.RequestForWorkerDTO;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.workplace.WorkplaceController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/workers")
public class PharmacyWorkerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PharmacyWorkerService pharmacyWorkerService;

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
    public ResponseEntity<List<PharmacyWorkerFreePharmacistDTO>> getPharmaciesByFreePharmacists(Pageable pageable, @RequestParam(value = "date", required = false) long date, @RequestParam(value = "id", required = false) Long id) {
        List<PharmacyWorker> workers = pharmacyWorkerService.getFreePharmacistsByPharmacyIdAndDate(id, date, pageable.getSort());

        if(workers == null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        List<PharmacyWorkerFreePharmacistDTO> retVal = new ArrayList<>();
        for(PharmacyWorker pw : workers) {
            retVal.add(new PharmacyWorkerFreePharmacistDTO(pw));
        }

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping(value = "/notexistingworkplacebypharmacyid/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotWorkingWorkersByPharmacyId(@PathVariable("pharmacyId") Long pharmacyId, @RequestBody RequestForWorkerDTO dto) {
        if (WorkplaceController.checkDto(dto)) return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);

        List<PharmacyWorker> workers = pharmacyWorkerService.getNotWorkingWorkersByPharmacyId(pharmacyId, dto);

        List<PharmacyWorkerDTO> retVal = new ArrayList<>();
        for(PharmacyWorker pw : workers) {
            retVal.add(convertToDto(pw));
        }

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    private PharmacyWorkerDTO convertToDto(PharmacyWorker worker) {
        return modelMapper.map(worker, PharmacyWorkerDTO.class);
    }


}
