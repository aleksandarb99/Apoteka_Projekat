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
}
