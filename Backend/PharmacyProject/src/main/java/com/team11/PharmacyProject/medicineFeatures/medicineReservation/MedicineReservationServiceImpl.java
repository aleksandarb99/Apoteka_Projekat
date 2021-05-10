package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationWorkerDTO;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import com.team11.PharmacyProject.workplace.Workplace;
import com.team11.PharmacyProject.workplace.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MedicineReservationServiceImpl implements MedicineReservationService  {

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    MedicineReservationRepository reservationRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    WorkplaceService workplaceService;

    @Override
    public boolean isMedicineItemReserved(Long id) {
        MedicineReservation reservation =  reservationRepository.findReservationByMedicineItemId(id);
        
        if(reservation != null)
            return true;
        return false;
    }

    @Override
    public List<MedicineReservation> getReservedMedicinesByPatientId(Long id) {

        Patient patient = patientRepository.findPatientFetchReservedMedicines(id, System.currentTimeMillis());

        if(patient == null) return null;

        return patient.getMedicineReservation();
    }

    @Override
    public MedicineReservationNotifyPatientDTO insertMedicineReservation(MedicineReservationInsertDTO dto) {

        Optional<Patient> p = patientRepository.findById(dto.getUserId());
        if(p.isPresent()) {
            if(p.get().getPenalties() == 3) return null;
        }

        Pharmacy pharmacy = pharmacyRepository.findPharmacyByPharmacyAndMedicineId(dto.getPharmacyId(), dto.getMedicineId());
        if (pharmacy == null) return null;

        MedicineItem item = null;

        for(MedicineItem mi : pharmacy.getPriceList().getMedicineItems()) {
            if(mi.getMedicine().getId().equals(dto.getMedicineId())) {
                item = mi;
                break;
            }
        }

        if(item == null) return null;
        if(!item.setAmountLessOne()) return null;

        MedicineReservation reservation = new MedicineReservation();
        reservation.setReservationID(UUID.randomUUID().toString());
        reservation.setPharmacy(pharmacy);
        reservation.setPickupDate(dto.getPickupDate());
        reservation.setReservationDate(System.currentTimeMillis());
        reservation.setState(ReservationState.RESERVED);
        reservation.setMedicineItem(item);

        Optional<Patient> patient = Optional.ofNullable(patientRepository.findByIdAndFetchReservationsEagerly(dto.getUserId()));
        if(patient.isEmpty()) {
            patient = patientRepository.findById(dto.getUserId());
            if(patient.isEmpty()) return null;
            patient.get().setMedicineReservation(new ArrayList<>());
        }

        if(!patient.get().addReservation(reservation)) return null;

        pharmacyRepository.save(pharmacy);
        patientRepository.save(patient.get());

        return new MedicineReservationNotifyPatientDTO(patient.get(), pharmacy, reservation);
    }

    @Override
    public boolean cancelReservation(Long id) {
        Optional<MedicineReservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty()) return false;

        MedicineReservation reservation = reservationOptional.get();

        if (reservation.getState() != ReservationState.RESERVED) return false;
        if (reservation.getPickupDate() < System.currentTimeMillis()) return false; // Provera da ipak nije rezervacija iz proslosti

        long differenceInMinutes = ((reservation.getPickupDate() - System.currentTimeMillis()) / (1000 * 60));
        if(differenceInMinutes < 1440) return false;

        reservation.setState(ReservationState.CANCELLED);

        reservationRepository.save(reservation);
        return true;
    }

    @Override
    public MedicineReservation getMedicineReservationFromPharmacy(Long workerID, String resID){
        Workplace workplace = workplaceService.getWorkplaceOfPharmacist(workerID);
        if (workplace == null){
            return null;
        }
        return reservationRepository.getMedicineReservationFromPharmacy(workplace.getPharmacy().getId(),resID);
    }

    @Override
    public MedicineReservationWorkerDTO issueMedicine(Long workerID, String resID){
        //todo srediti da ima i cenu mozda
        MedicineReservation medicineReservation = getMedicineReservationFromPharmacy(workerID, resID);
        if (medicineReservation == null) {
            return null;
        }
        Long dueDate = medicineReservation.getPickupDate();
        Long currTime = Instant.now().toEpochMilli();
        if (dueDate - currTime <= 0){
            return null;
        }else if (TimeUnit.MILLISECONDS.toHours(dueDate-currTime) < 24){  //manje od 24 h do izdavanja
            return null;
        }
        Patient pat = patientRepository.findByReservationID(medicineReservation.getId());
        if (pat == null){
            return null;
        }

        medicineReservation.setState(ReservationState.RECEIVED);
        reservationRepository.save(medicineReservation);

        return new MedicineReservationWorkerDTO(medicineReservation, pat);
    }
}
