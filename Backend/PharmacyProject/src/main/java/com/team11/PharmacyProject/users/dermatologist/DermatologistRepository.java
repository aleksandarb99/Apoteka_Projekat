//package com.team11.PharmacyProject.users.dermatologist;
//
//import com.team11.PharmacyProject.appointment.Appointment;
//import com.team11.PharmacyProject.enums.AppointmentState;
//import com.team11.PharmacyProject.enums.AppointmentType;
//import com.team11.PharmacyProject.enums.UserType;
//import com.team11.PharmacyProject.location.Location;
//import com.team11.PharmacyProject.pharmacy.Pharmacy;
//import com.team11.PharmacyProject.priceList.PriceList;
//import com.team11.PharmacyProject.users.patient.Patient;
//import com.team11.PharmacyProject.workplace.Workplace;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class DermatologistRepository {
//    List<Dermatologist> dermatologistList;
//
//    public DermatologistRepository(){
//        this.dermatologistList = new ArrayList<Dermatologist>();
//
//        Dermatologist derm = new Dermatologist(1L, "djuric", "djura", "djorko", "djole@gmail.com", "092920119",
//                UserType.DERMATOLOGIST, null, 3.4, null, new ArrayList<>(), new ArrayList<>());
//
//        dermatologistList.add(derm);
//
//        Pharmacy pharmacy = new Pharmacy(1L, "Zelena Apoteka", "Najbolja apoteka u gradu.",
//                4.2, null, null, null,
//                null, null);
//
//        Pharmacy pharmacy2 = new Pharmacy(2L, "Apoteka Jankovic", "Najbolja apoteka u gradu.",
//                4.2, null, null, null,
//                null, null);
//
//        Appointment appt = new Appointment(1L, LocalDate.now(), null, 69, AppointmentState.EMPTY,
//                null, 4000, AppointmentType.CHECKUP, null, null, pharmacy);
//
//        Patient pat = new Patient(1L, "pera", "Pera", "Zdera", "pera@mail.com", "980809890",
//                UserType.PATIENT, null, 3, 0, new ArrayList<>(), new ArrayList<>());
//
//        Patient pat2 = new Patient(2L, "pera", "Mika", "Micke", "pera@mail.com", "980809890",
//                UserType.PATIENT, null, 3, 0, new ArrayList<>(), new ArrayList<>());
//
//        Appointment appt2 = new Appointment(2L, LocalDate.now().plusDays(3), null, 69, AppointmentState.EMPTY,
//                null, 3000, AppointmentType.CHECKUP, pat, null, pharmacy2);
//
//        Appointment appt3 = new Appointment(3L, LocalDate.now().plusDays(3), null, 69, AppointmentState.EMPTY,
//                null, 3000, AppointmentType.CHECKUP, pat2, null, pharmacy2);
//
//        derm.getAppointmentList().add(appt);
//        derm.getAppointmentList().add(appt2);
//        derm.getAppointmentList().add(appt3);
//
//    }
//
//    public List<Appointment> getDermatologistsUpcommingAppointments(long id){
//        List<Appointment> appts = new ArrayList<>();
//        LocalDate today = LocalDate.now();
//        for (Dermatologist derm : dermatologistList) {
//            if (derm.getId() == id){
//                for (Appointment apt: derm.getAppointmentList()) {
//                    if (apt.getStartTime().compareTo(LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth())) >= 0
//                    && (apt.getStartTime().compareTo(LocalDate.of(today.getYear(), today.getMonth(), today.plusDays(7).getDayOfMonth()))) < 0){
//                        appts.add(apt);
//                    }
//                }
//            }
//        }
//        return appts;
//    }
//}
