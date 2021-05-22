package com.team11.PharmacyProject.advertisement;

import com.team11.PharmacyProject.dto.advertisment.AdvertismentDTORequest;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.enums.AdvertisementType;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemService;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertismentServiceImpl implements AdvertismentService {

    @Autowired
    AdvertismentRepository advertismentRepository;

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    EmailService emailService;

    @Autowired
    MedicineItemService medicineItemService;

    @Override
    public List<Advertisement> findAll(Long pharmacyId) {
        List<Advertisement> list = advertismentRepository.findAll(pharmacyId);
        return list;
    }

    @Override
    public List<Advertisement> findAllSalesWithDate(Long pharmacyId, Long medicineId, long currentTimeMillis) {
        return advertismentRepository.findAllSalesWithDate(pharmacyId, medicineId, currentTimeMillis);
    }

    @Override
    public void addAdvertisment(Long pharmacyId, AdvertismentDTORequest dto) {

        int type2 = Integer.parseInt(dto.getType())-1;
        AdvertisementType type;
        if(type2 == 0) {
            type = AdvertisementType.PROMOTION;
            dto.setDiscount(0);
            if(dto.getText().equals(""))
                throw new RuntimeException("Promotion should have content!");
        }
        else {
            type = AdvertisementType.SALE;
            dto.setText("");
            if(dto.getDiscount() <= 0 || dto.getDiscount() > 100)
                throw new RuntimeException("Discount range is beetwen 0 ad 100!");
        }

        Pharmacy pharmacy = pharmacyService.getPharmacyWithSubsribers(pharmacyId);
        if(pharmacy == null)
            throw new RuntimeException("Pharmacy with id " + pharmacyId + " does not exist!");


        MedicineItem item = medicineItemService.findByIdWithMedicine(dto.getSelectedRowId());
        if(item == null)
            throw new RuntimeException("Medicine item with id " + dto.getSelectedRowId() + " does not exist!");

        Advertisement advertisement = new Advertisement(System.currentTimeMillis(), dto.getEndDate(), dto.getText(), pharmacy, dto.getDiscount(), type, item);
        advertismentRepository.save(advertisement);

        try {
            emailService.notifySubsribers(advertisement, pharmacy);
        } catch (Exception e) {
            throw new RuntimeException("Error with mail sending!");
        }

    }
}
