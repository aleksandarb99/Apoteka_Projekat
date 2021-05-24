package com.team11.PharmacyProject.requestForHoliday;

import com.team11.PharmacyProject.dto.requestForHoliday.RequestForHolidayDTO;
import com.team11.PharmacyProject.dto.requestForHoliday.RequestForHolidayWithWorkerDetailsDTO;
import com.team11.PharmacyProject.enums.AbsenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/vacation")
public class RequestForHolidayController {
    @Autowired
    RequestForHolidayServiceImpl requestForHolidayService;

    @GetMapping(value = "/getVacationsFromWorker", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RequestForHolidayDTO>> getVacationsFromWorker(@RequestParam("id") Long id){
        List<RequestForHoliday> requestForHolidays = requestForHolidayService.getWorkerHolidays(id);
        List<RequestForHolidayDTO> dtos = new ArrayList<>(requestForHolidays.size());
        for (int i = requestForHolidays.size()-1; i >= 0; i--){ //da bi se poslednji zahtev pokazao prvi na listi na fronu
            dtos.add(new RequestForHolidayDTO(requestForHolidays.get(i)));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getunresolvedrequestsbypharmacyid/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PHARMACY_ADMIN', 'ADMIN')")
    public ResponseEntity<List<RequestForHolidayWithWorkerDetailsDTO>> getUnresolvedRequestsByPharmacy(@PathVariable("pharmacyId") Long pharmacyId){
        List<RequestForHoliday> requestForHolidays = requestForHolidayService.getUnresolvedRequestsByPharmacy(pharmacyId);
        List<RequestForHolidayWithWorkerDetailsDTO> dtos = new ArrayList<>(requestForHolidays.size());
        for (RequestForHoliday req : requestForHolidays){
            dtos.add(new RequestForHolidayWithWorkerDetailsDTO(req));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/acceptrequest/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PHARMACY_ADMIN', 'ADMIN')")
    public ResponseEntity<String> acceptRequest(@PathVariable("requestId") String requestId){
        try {
            requestForHolidayService.acceptRequest(requestId);
            return new ResponseEntity<>("Successfully accepted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/rejectrequest/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PHARMACY_ADMIN', 'ADMIN')")
    public ResponseEntity<String> rejectRequest(@PathVariable("requestId") String requestId, @RequestBody String reason){
        try {
            requestForHolidayService.rejectRequest(requestId, reason);
            return new ResponseEntity<>("Successfully rejected", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getAcceptedVacationsFromWorker", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RequestForHolidayDTO>> getAcceptedVacationsFromWorker(@RequestParam("id") Long id){
        List<RequestForHoliday> requestForHolidays = requestForHolidayService.getAcceptedWorkerHolidays(id);
        List<RequestForHolidayDTO> dtos = new ArrayList<>(requestForHolidays.size());
        for (RequestForHoliday req : requestForHolidays){
            dtos.add(new RequestForHolidayDTO(req));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/request_vacation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> requestVacation(@RequestParam("id") Long id, @RequestParam("start") Long start,
                                                    @RequestParam("end") Long end, @RequestParam("type") String type){
        //todo provera za datum da ne bude u proslosti, i za ono vece manje
        if (Instant.now().toEpochMilli() > start){
            return new ResponseEntity<>("Error! Start date has to be in future!", HttpStatus.BAD_REQUEST);
        }else if (start >= end){
            return new ResponseEntity<>("Error! Start invalid start/end date!", HttpStatus.BAD_REQUEST);
        }
        AbsenceType absenceType;
        if (type.equalsIgnoreCase("vacation"))
            absenceType = AbsenceType.VACATION;
        else
            absenceType = AbsenceType.LEAVE;

        String resp = requestForHolidayService.createHolidayRequest(id, start, end, absenceType);
        if (resp.startsWith("Error")){
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }
}
