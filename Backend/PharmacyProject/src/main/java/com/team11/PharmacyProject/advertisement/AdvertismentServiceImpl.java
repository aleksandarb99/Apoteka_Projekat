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
    public boolean addAdvertisment(Long pharmacyId, AdvertismentDTORequest dto) {

        int type2 = Integer.parseInt(dto.getType())-1;
        AdvertisementType type;
        if(type2 == 0) {
            type = AdvertisementType.PROMOTION;
            dto.setDiscount(0);
            if(dto.getText().equals(""))
                return false;
        }
        else {
            type = AdvertisementType.SALE;
            dto.setText("");
            if(dto.getDiscount() <= 0 || dto.getDiscount() > 100)
                return false;
        }

        Pharmacy pharmacy = pharmacyService.getPharmacyWithSubsribers(pharmacyId);
        if(pharmacy == null)
            return false;


        MedicineItem item = medicineItemService.findByIdWithMedicine(dto.getSelectedRowId());
        if(item == null)
            return false;

        Advertisement advertisement = new Advertisement(System.currentTimeMillis(), dto.getEndDate(), dto.getText(), pharmacy, dto.getDiscount(), type, item);
        advertismentRepository.save(advertisement);

        try {
            emailService.notifySubsribers(advertisement, pharmacy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
