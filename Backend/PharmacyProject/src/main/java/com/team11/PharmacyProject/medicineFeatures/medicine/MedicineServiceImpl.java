package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import com.team11.PharmacyProject.priceList.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private PriceListService priceListService;

    @Override
    public Medicine findOne(long id) {
        Optional<Medicine> medicine = medicineRepository.findById(id);
        return medicine.orElse(null);
    }

    @Override
    public List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        medicineRepository.findAll().forEach(medicines::add);
        return medicines;
    }

    @Override
    public List<Medicine> getNotExistingMedicineFromPharmacy(long id) {
        List<Medicine> allMedicine = new ArrayList<>();
        medicineRepository.findAll().forEach(allMedicine::add);

        List<Medicine> medicineList = new ArrayList<>();

        Pharmacy p = pharmacyService.getPharmacyByIdAndPriceList(id);

        if(p==null){
            return medicineList;
        }

        for (Medicine m: allMedicine) {
            boolean flag = false;
            for (MedicineItem mi: priceListService.findByIdAndFetchMedicineItems(p.getPriceList().getId()).getMedicineItems()) {
                if(m.getId().equals(mi.getMedicine().getId())){
                    flag = true;
                }
            }
            if(!flag){
                medicineList.add(m);
            }
        }
        return medicineList;
    }

    @Override
    public boolean insertMedicine(Medicine medicine) {
        if (medicine != null) {
            medicineRepository.save(medicine);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        if (medicineRepository.findById(id).isPresent()) {
            medicineRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(long id, Medicine medicine) {
        Optional<Medicine> m = medicineRepository.findById(id);
        if (m.isPresent()) {
            Medicine m2 = m.get();
            if (medicine.getCode() != null) {
                m2.setCode(medicine.getCode());
            }
            if (medicine.getName() != null) {
                m2.setName(medicine.getName());
            }
            if (medicine.getContent() != null) {
                m2.setContent(medicine.getContent());
            }
            if (medicine.getSideEffects() != null) {
                m2.setSideEffects(medicine.getSideEffects());
            }
            if (medicine.getRecipeRequired() != null) {
                m2.setRecipeRequired(medicine.getRecipeRequired());
            }
            m2.setDailyIntake(medicine.getDailyIntake());
            if (medicine.getAdditionalNotes() != null) {
                m2.setAdditionalNotes(medicine.getAdditionalNotes());
            }
            medicineRepository.save(m2);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findByIdAndFetchFormTypeManufacturer(id);
    }

    @Override
    public ByteArrayInputStream getMedicinePdf(long medicineId) {
        Medicine medicine = medicineRepository.findByIdAndFetchFormTypeManufacturer(medicineId);
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Chunk chunk = new Chunk("NASLOV", font);

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(chunk);
            document.close();

        } catch (DocumentException ex) {
            System.err.println("ERROR. PDF file not generated");
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
