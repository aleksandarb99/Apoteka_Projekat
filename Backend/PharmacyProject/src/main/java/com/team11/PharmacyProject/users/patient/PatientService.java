package com.team11.PharmacyProject.users.patient;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineRepository;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservationRepository;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    MedicineReservationRepository reservationRepository;

    public List<Patient> searchPatientsByFirstAndLastName(String firstname, String lastname){
        return patientRepository.searchPatientsByFirstAndLastName(firstname, lastname);
    }


    public List<Patient> getExaminedPatients(Long workerID,
                                              String firstName,
                                              String lastName,
                                              Long lowerTime,
                                              Long upperTime, Sort sorter){
        return patientRepository.getExaminedPatients(workerID, firstName, lastName, lowerTime, upperTime, sorter);
    }

    public List<Patient> getAllExaminedPatients(Long workerID) {
        return patientRepository.getAllExaminedPatients(workerID);
    }

    public Patient findOne(Long id) {
        return patientRepository.findByIdAndFetchAllergiesEagerly(id);
    }

    public void deleteAllergy(long id, long allergy_id) {
        Patient patient = patientRepository.findByIdAndFetchAllergiesEagerly(id);
        if (patient != null) {
            if(!patient.removeAllergy(allergy_id))
                throw new RuntimeException("Patient doesn't have selected allergy!");

            patientRepository.save(patient);
        } else {
            throw new RuntimeException("Patient does not exist in the database!");
        }
    }

    public void addAllergy(long id, long allergy_id) {

        Patient patient = patientRepository.findByIdAndFetchAllergiesEagerly(id);
        if (patient == null)
            throw new RuntimeException("Patient does not exist in the database!");

        Optional<Medicine> medicine = medicineRepository.findById(allergy_id);
        if(medicine.isEmpty())
            throw new RuntimeException("Medicine does not exist in the database!");
        Medicine allergy = medicine.get();

        if(!patient.addAllergy(allergy))
            throw new RuntimeException("You already have this medicine recorded as allergy!");

        patientRepository.save(patient);

    }

    public List<Patient> getAll(){
        return (List<Patient>) patientRepository.findAll();
    }

    public List<Patient> getAllAndFetchAddress(){
        return patientRepository.getAllAndFetchAddress();
    }

    public Patient getPatient(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isEmpty()) return null;
        return patient.get();
    }

    public List<PharmacyWorker> getMyPharmacists(long patientId) {
        return getMyPharmacyWorkers(patientId, "PHARMACIST");
    }

    public List<PharmacyWorker> getMyDermatologists(long patientId) {
        return getMyPharmacyWorkers(patientId, "DERMATOLOGIST");
    }

    private List<PharmacyWorker> getMyPharmacyWorkers(long patientId, String pharmacyWorkerType) {
        Patient patient = patientRepository.findByIdAndFetchAppointments(patientId);
        List<Appointment> apps = patient.getAppointments();
        return apps
                .stream()
                .filter(appointment -> appointment.getEndTime() < System.currentTimeMillis()
                        && appointment.getAppointmentState() == AppointmentState.FINISHED)
                .map(Appointment::getWorker)
                .filter(pharmacyWorker -> pharmacyWorker.getRole().getName().equals(pharmacyWorkerType))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Pharmacy> getMyPharmacies(long patientId) {
        Patient patientAppointments = patientRepository.findByIdAndFetchAppointments(patientId);

        // Dobavi one apoteke u kojima je imao pregled
        List<Appointment> apps = patientAppointments.getAppointments();
        List<Pharmacy> pharmaciesWithAppointments = apps
                .stream()
                .filter(appointment -> appointment.getEndTime() < System.currentTimeMillis()
                        && appointment.getAppointmentState() == AppointmentState.FINISHED)
                .map(Appointment::getPharmacy)
                .distinct()
                .collect(Collectors.toList());

        // Dobavi apoteke iz kojih je preuzimao recept
        Patient patientReservations = patientRepository.findByIdAndFetchReservations(patientId);

        List<MedicineReservation> medicineReservations = patientReservations.getMedicineReservation();
        List<Pharmacy> pharmaciesWithReservations = medicineReservations
                .stream()
                .map(MedicineReservation::getPharmacy)
                .distinct()
                .collect(Collectors.toList());

        // Spoji ove dve liste
        return getDistinctPharmaciesById(pharmaciesWithAppointments, pharmaciesWithReservations);
    }

    public List<Pharmacy> getDistinctPharmaciesById(List<Pharmacy> p1, List<Pharmacy> p2) {
        p1.addAll(p2);
        Set<Long> pharmacySet = new HashSet<>(p1.size());
        return p1.stream().filter(p -> pharmacySet.add(p.getId())).collect(Collectors.toList());
    }

    public void givePenaltyForNotPickedUpOrCanceledReservation() {
        List<Patient> patients = patientRepository.findAllFetchReservations(System.currentTimeMillis());

        for(Patient p : patients) {
            for(MedicineReservation mr : p.getMedicineReservation()) {
                p.setPenalties(p.getPenalties() + 1);
                mr.setState(ReservationState.NOT_RECEIVED);
                reservationRepository.save(mr);     // Promenimo u NOT_RECEIVED da i sutradan ne bi gledao ovaj reservation i dao mu opet penal
            }
            patientRepository.save(p);
        }
    }

    public void resetPenalties() {
        List<Patient> patients = patientRepository.findAll();

        for(Patient p : patients) {
            p.setPenalties(0);
            patientRepository.save(p);
        }
    }
}
