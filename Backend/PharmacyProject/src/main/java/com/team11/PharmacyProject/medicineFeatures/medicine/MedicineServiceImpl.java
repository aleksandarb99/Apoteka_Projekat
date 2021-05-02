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
        Medicine medicine = medicineRepository.findByIdAndFetchFormTypeManufacturerAlternative(medicineId);
        if (medicine == null)
            return null;
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Font titleFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.BLACK);

            Paragraph nameParagraph = new Paragraph(medicine.getName(), titleFont);
            nameParagraph.setAlignment(Element.ALIGN_CENTER);

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(nameParagraph);
            document.add(Chunk.NEWLINE);
            document.add(generateParagraph("1. Kod", medicine.getCode()));
            document.add(generateParagraph("2. Naziv", medicine.getName()));
            document.add(generateParagraph("3. Sastojci", medicine.getContent()));
            document.add(generateParagraph("4. Neželjena dejstva", medicine.getSideEffects()));
            document.add(generateParagraph("5. Tip leka", medicine.getMedicineType().getName()));
            document.add(generateParagraph("6. Oblik leka", medicine.getMedicineForm().getName()));
            document.add(generateParagraph("7. Prosečna ocena", String.valueOf(medicine.getAvgGrade())));
            document.add(generateParagraph("8. Dodatne napomene", medicine.getAdditionalNotes()));
            document.add(generateAlternativesParagraph("9. Zamenski lekovi", medicine));
            document.close();

        } catch (DocumentException ex) {
            System.err.println("ERROR. PDF file not generated");
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private Paragraph generateParagraph(String title, String text) {
        Font headerFont = FontFactory.getFont(FontFactory.COURIER, "Cp1250", true);
        headerFont.setSize(12);
        headerFont.setStyle(Font.BOLD);
        Font textFont = FontFactory.getFont(FontFactory.COURIER, "Cp1250", true);
        textFont.setSize(12);

        Phrase headerPhrase = new Phrase(String.format("%s\n", title), headerFont);
        Paragraph textParagraph = new Paragraph(text, textFont);
        textParagraph.setIndentationLeft(12f);
        Paragraph p = new Paragraph();
        p.add(headerPhrase);
        p.add(textParagraph);
        p.setSpacingAfter(14f);
        return p;
    }

    private Paragraph generateAlternativesParagraph(String title, Medicine m) {
        Font headerFont = FontFactory.getFont(FontFactory.COURIER, "Cp1250", true);
        headerFont.setSize(12);
        headerFont.setStyle(Font.BOLD);
        Font textFont = FontFactory.getFont(FontFactory.COURIER, "Cp1250", true);
        textFont.setSize(12);

        Phrase headerPhrase = new Phrase(String.format("%s\n", title), headerFont);
        Paragraph p = new Paragraph();
        p.add(headerPhrase);

        Paragraph bodyParagraph = new Paragraph();
        for (Medicine alt: m.getAlternativeMedicine()) {
            Chunk c = new Chunk(String.format("%s - %s\n", alt.getName(), alt.getCode()), textFont);
            // TODO change base URL if needed
            String baseURL = "http://localhost:8080/api/medicine/{id}/get-pdf";
            String altUrl = baseURL.replace("{id}", alt.getId().toString());
            c.setAnchor(altUrl);
            bodyParagraph.add(c);
        }
        if (m.getAlternativeMedicine().isEmpty()) {
            bodyParagraph.add(new Chunk("Nema zamenskih lekova", textFont));
        }

        bodyParagraph.setIndentationLeft(12f);

        p.add(bodyParagraph);
        p.setSpacingAfter(14f);
        return p;
    }
}
