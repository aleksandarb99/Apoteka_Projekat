//package com.team11.PharmacyProject.users.dermatologist;
//
//import com.team11.PharmacyProject.address.Address;
//import com.team11.PharmacyProject.appointment.Appointment;
//import com.team11.PharmacyProject.dermatologistWorkplace.Workplace;
//import com.team11.PharmacyProject.enums.UserType;
//import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
//import com.team11.PharmacyProject.workCalendar.WorkCalendar;
//
//import java.util.List;
//
//public class Dermatologist extends PharmacyWorker {
//    private List<Workplace> workplaces;
//
//    public Dermatologist(Long id, String password, String firstName, String lastName, String email, String telephone,
//                         UserType userType, Address address, double avgGrade, WorkCalendar calendar, List<Appointment> list, List<Workplace> workplaces) {
//        super(id, password, firstName, lastName, email, telephone, userType, address, avgGrade, calendar, list);
//        this.workplaces = workplaces;
//    }
//
//    public List<Workplace> getWorkplaces() {
//        return workplaces;
//    }
//
//    public void setWorkplaces(List<Workplace> workplaces) {
//        this.workplaces = workplaces;
//    }
//}