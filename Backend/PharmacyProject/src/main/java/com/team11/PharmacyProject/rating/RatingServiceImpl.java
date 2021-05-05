package com.team11.PharmacyProject.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService{

    @Autowired
    RatingRepository ratingRepository;

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
}
