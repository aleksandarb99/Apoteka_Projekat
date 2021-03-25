//package com.team11.PharmacyProject.users.dermatologist;
//
//import com.team11.PharmacyProject.appointment.Appointment;
//import com.team11.PharmacyProject.pharmacy.Pharmacy;
//import com.team11.PharmacyProject.pharmacy.PharmacyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/dermatologist/upcomming")
//public class DermatologistController {
//
//    @Autowired
//    DermatologistService dermatologistService;
//
//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Appointment>> getUpcommingAppointments(@PathVariable("id") Long id){
//        List<Appointment> appts = dermatologistService.getDermatologistsUpcommingAppointments(id);
//
//        if(appts == null){
//            return new ResponseEntity<List<Appointment>>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<List<Appointment>>(appts, HttpStatus.OK);
//    }
//}
