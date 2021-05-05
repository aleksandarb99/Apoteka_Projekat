package com.team11.PharmacyProject.rating;

public interface RatingService {
    Rating getDermatologistGrade(Long dId, Long pId);

    Rating getPharmacistGrade(Long pId, Long paId);

    Rating getMedicineGrade(Long mId, Long paId);

    Rating getPharmacyGrade(Long pId, Long paId);
}
