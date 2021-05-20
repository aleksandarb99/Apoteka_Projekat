package com.team11.PharmacyProject.advertisement;

import com.team11.PharmacyProject.dto.advertisment.AdvertismentDTORequest;

import java.util.List;

public interface AdvertismentService {

    List<Advertisement> findAll(Long pharmacyId);

    boolean addAdvertisment(Long id, AdvertismentDTORequest dto);

    List<Advertisement> findAllSalesWithDate(Long pharamcyId, Long medicineId, long currentTimeMillis);
}
