package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicineReservationServiceImpl implements MedicineReservationService  {

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    MedicineReservationRepository reservationRepository;

    @Autowired
    PatientRepository patientRepository;

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
}
