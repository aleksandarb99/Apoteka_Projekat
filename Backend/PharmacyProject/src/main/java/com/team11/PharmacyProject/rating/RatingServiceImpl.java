package com.team11.PharmacyProject.rating;

import com.team11.PharmacyProject.dto.rating.RatingCreateUpdateDTO;
import com.team11.PharmacyProject.enums.GradedType;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineRepository;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService{

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    PharmacyWorkerRepository workerRepository;

    @Autowired
    MedicineRepository medicineRepository;

    @Override
    public Rating getDermatologistGrade(Long dId, Long pId) {

        return ratingRepository.findDermatologistRatingFetchPatient(dId, pId);
    }

    @Override
    public Rating getPharmacistGrade(Long pId, Long paId) {

        return ratingRepository.findPharmacistRatingFetchPatient(pId, paId);
    }

    @Override
    public Rating getMedicineGrade(Long mId, Long paId) {

        return ratingRepository.findMedicineRatingFetchPatient(mId, paId);
    }

    @Override
    public Rating getPharmacyGrade(Long pId, Long paId) {

        return ratingRepository.findPharmacyRatingFetchPatient(pId, paId);
    }

    @Override
    public boolean addRating(RatingCreateUpdateDTO dto) {

        Optional<Rating> rating = ratingRepository.findById(dto.getId());
        if(rating.isPresent()) return false;

        Optional<Patient> patient = patientRepository.findById(dto.getPatientId());
        if (patient.isEmpty()) return false;

        if (dto.getType().equals(GradedType.DERMATOLOGIST)) {
            Optional<PharmacyWorker> worker = workerRepository.findById(dto.getGradedID());
            if (worker.isEmpty()) return false;
            Rating existRating = ratingRepository.findIfRatingExistsDermatologist(dto.getGradedID(), dto.getPatientId());
            if (existRating != null) return false;
            List<Rating> ratings = ratingRepository.findAllRatingsByDermatologistId(worker.get().getId());
            if (ratings == null) {
                worker.get().setAvgGrade(dto.getGrade());
            } else {
                double sum = 0;
                for (Rating r : ratings) {
                    sum += r.getGrade();
                }
                sum += dto.getGrade();
                worker.get().setAvgGrade(sum / (ratings.size() + 1));
            }
            workerRepository.save(worker.get());
        } else if (dto.getType().equals(GradedType.PHARMACIST)) {
            Optional<PharmacyWorker> worker = workerRepository.findById(dto.getGradedID());
            if (worker.isEmpty()) return false;
            Rating existRating = ratingRepository.findIfRatingExistsPharmacist(dto.getGradedID(), dto.getPatientId());
            if (existRating != null) return false;
            List<Rating> ratings = ratingRepository.findAllRatingsByPharmacistId(worker.get().getId());
            if (ratings == null) {
                worker.get().setAvgGrade(dto.getGrade());
            } else {
                double sum = 0;
                for (Rating r : ratings) {
                    sum += r.getGrade();
                }
                sum += dto.getGrade();
                worker.get().setAvgGrade(sum / (ratings.size() + 1));
            }
            workerRepository.save(worker.get());
        } else if (dto.getType().equals(GradedType.PHARMACY)) {
            Optional<Pharmacy> pharmacy = pharmacyRepository.findById(dto.getGradedID());
            if (pharmacy.isEmpty()) return false;
            Rating existRating = ratingRepository.findIfRatingExistsPharmacy(dto.getGradedID(), dto.getPatientId());
            if (existRating != null) return false;
            List<Rating> ratings = ratingRepository.findAllRatingsByPharmacyId(pharmacy.get().getId());
            if (ratings == null) {
                pharmacy.get().setAvgGrade((double) dto.getGrade());
            } else {
                double sum = 0;
                for (Rating r : ratings) {
                    sum += r.getGrade();
                }
                sum += dto.getGrade();
                pharmacy.get().setAvgGrade(sum / (ratings.size() + 1));
            }
            pharmacyRepository.save(pharmacy.get());
        } else if (dto.getType().equals(GradedType.MEDICINE)) {
            Optional<Medicine> medicine = medicineRepository.findById(dto.getGradedID());
            if (medicine.isEmpty()) return false;
            Rating existRating = ratingRepository.findIfRatingExistsMedicine(dto.getGradedID(), dto.getPatientId());
            if (existRating != null) return false;
            List<Rating> ratings = ratingRepository.findAllRatingsByMedicineId(medicine.get().getId());
            if (ratings == null) {
                medicine.get().setAvgGrade(dto.getGrade());
            } else {
                double sum = 0;
                for (Rating r : ratings) {
                    sum += r.getGrade();
                }
                sum += dto.getGrade();
                medicine.get().setAvgGrade(sum / (ratings.size() + 1));
                medicineRepository.save(medicine.get());
            }
        } else {
            return  false;
        }

        Rating newRating = new Rating();
        newRating.setDate(dto.getDate());
        newRating.setGrade(dto.getGrade());
        newRating.setGradedType(dto.getType());
        newRating.setGradedID(dto.getGradedID());
        newRating.setPatient(patient.get());

        ratingRepository.save(newRating);

        return true;
    }

    @Override
    public boolean editRating(RatingCreateUpdateDTO dto) {

        Optional<Rating> rating = ratingRepository.findById(dto.getId());
        if(rating.isEmpty()) return false;

        Optional<Patient> patient = patientRepository.findById(dto.getPatientId());
        if (patient.isEmpty()) return false;

        if (dto.getType().equals(GradedType.DERMATOLOGIST)) {
            Optional<PharmacyWorker> worker = workerRepository.findById(dto.getGradedID());
            if (worker.isEmpty()) return false;
            Rating existRating = ratingRepository.findIfRatingExistsDermatologist(dto.getGradedID(), dto.getPatientId());
            if (existRating == null) return false;
            List<Rating> ratings = ratingRepository.findAllRatingsByDermatologistId(worker.get().getId());
            double sum = 0;
            for (Rating r : ratings) {
                if (r.getId().equals(rating.get().getId())) continue;
                sum += r.getGrade();
            }
            sum += dto.getGrade();
            worker.get().setAvgGrade(sum / ratings.size());
            workerRepository.save(worker.get());
        } else if (dto.getType().equals(GradedType.PHARMACIST)) {
            Optional<PharmacyWorker> worker = workerRepository.findById(dto.getGradedID());
            if (worker.isEmpty()) return false;
            Rating existRating = ratingRepository.findIfRatingExistsPharmacist(dto.getGradedID(), dto.getPatientId());
            if (existRating == null) return false;
            List<Rating> ratings = ratingRepository.findAllRatingsByPharmacistId(worker.get().getId());
            double sum = 0;
            for (Rating r : ratings) {
                if (r.getId().equals(rating.get().getId())) continue;
                sum += r.getGrade();
            }
            sum += dto.getGrade();
            worker.get().setAvgGrade(sum / ratings.size());
            workerRepository.save(worker.get());
        } else if (dto.getType().equals(GradedType.PHARMACY)) {
            Optional<Pharmacy> pharmacy = pharmacyRepository.findById(dto.getGradedID());
            if (pharmacy.isEmpty()) return false;
            Rating existRating = ratingRepository.findIfRatingExistsPharmacy(dto.getGradedID(), dto.getPatientId());
            if (existRating == null) return false;
            List<Rating> ratings = ratingRepository.findAllRatingsByPharmacyId(pharmacy.get().getId());

            double sum = 0;
            for (Rating r : ratings) {
                if (r.getId().equals(rating.get().getId())) continue;
                sum += r.getGrade();
            }
            sum += dto.getGrade();
            pharmacy.get().setAvgGrade(sum / ratings.size());
            pharmacyRepository.save(pharmacy.get());
        } else if (dto.getType().equals(GradedType.MEDICINE)) {
            Optional<Medicine> medicine = medicineRepository.findById(dto.getGradedID());
            if (medicine.isEmpty()) return false;
            Rating existRating = ratingRepository.findIfRatingExistsMedicine(dto.getGradedID(), dto.getPatientId());
            if (existRating == null) return false;
            List<Rating> ratings = ratingRepository.findAllRatingsByMedicineId(medicine.get().getId());
            double sum = 0;
            for (Rating r : ratings) {
                if (r.getId().equals(rating.get().getId())) continue;
                sum += r.getGrade();
            }
            sum += dto.getGrade();
            medicine.get().setAvgGrade(sum / ratings.size());
            medicineRepository.save(medicine.get());
        } else {
            return  false;
        }

        rating.get().setDate(dto.getDate());
        rating.get().setGrade(dto.getGrade());

        ratingRepository.save(rating.get());

        return true;
    }
}
