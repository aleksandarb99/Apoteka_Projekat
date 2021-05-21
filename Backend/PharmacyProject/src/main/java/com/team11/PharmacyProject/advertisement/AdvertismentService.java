package com.team11.PharmacyProject.advertisement;

import com.team11.PharmacyProject.dto.advertisment.AdvertismentDTORequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdvertismentService {

    List<Advertisement> findAll(Long pharmacyId);

    boolean addAdvertisment(Long id, AdvertismentDTORequest dto);

    List<Advertisement> findAllSalesWithDate(Long pharmacyId, Long medicineId, long currentTimeMillis);
}
