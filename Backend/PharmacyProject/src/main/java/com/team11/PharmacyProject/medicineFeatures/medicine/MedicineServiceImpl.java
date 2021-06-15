package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.eRecipe.ERecipeService;
import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;
import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.exceptions.CustomException;
import com.team11.PharmacyProject.medicineFeatures.manufacturer.ManufacturerRepository;
import com.team11.PharmacyProject.medicineFeatures.medicineForm.MedicineFormRepository;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.medicineFeatures.medicineType.MedicineTypeRepository;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import com.team11.PharmacyProject.priceList.PriceListService;
import com.team11.PharmacyProject.search.SearchCriteria;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PriceListService priceListService;

    @Autowired
    private ERecipeService eRecipeService;

    @Autowired
    private MedicineFormRepository medicineFormRepository;

    @Autowired
    private MedicineTypeRepository medicineTypeRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public Medicine findOne(long id) {
        Optional<Medicine> medicine = medicineRepository.findById(id);
        return medicine.orElse(null);
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return new ArrayList<>(medicineRepository.fetchFormTypeManufacturerAlternative());
    }

    @Override
    public List<Medicine> getNotExistingMedicineFromPharmacy(long id) {
        List<Medicine> allMedicine = new ArrayList<>(medicineRepository.findAll());

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
    public void insertMedicine(Medicine medicine) throws CustomException {
        if (medicine == null) {
            throw new CustomException("Null medicine?");
        }

        if (medicine.getMedicineForm() != null) {
            var mf = medicineFormRepository.findByName(medicine.getMedicineForm().getName());
            mf.ifPresent(medicine::setMedicineForm);
        } else {
            throw new CustomException("Please set medicine form");
        }
        if (medicine.getMedicineType() != null) {
            var mt = medicineTypeRepository.findByName(medicine.getMedicineType().getName());
            mt.ifPresent(medicine::setMedicineType);
        } else {
            throw new CustomException("Please set medicine type");
        }
        if (medicine.getManufacturer() != null) {
            var man = manufacturerRepository.findByName(medicine.getManufacturer().getName());
            man.ifPresent(medicine::setManufacturer);
        } else {
            throw new CustomException("Please set manufacturer");
        }

        var tempList = new ArrayList<Medicine>();
        for (var ameddto: medicine.getAlternativeMedicine()) {
            var amed = medicineRepository.findByMedicineCode(ameddto.getCode());
            tempList.add(amed);
        }
        medicine.setAlternativeMedicine(tempList);
        medicineRepository.save(medicine);
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
    public boolean update(long id, Medicine medicine) throws CustomException {
        Medicine m = medicineRepository.findByIdAndFetchFormTypeManufacturerAlternative(id);
        if (m == null) {
            throw new CustomException("Null medicine?");
        }
        if (medicine.getCode() != null) {
            m.setCode(medicine.getCode());
        }
        if (medicine.getName() != null) {
            m.setName(medicine.getName());
        }
        if (medicine.getContent() != null) {
            m.setContent(medicine.getContent());
        }
        if (medicine.getSideEffects() != null) {
            m.setSideEffects(medicine.getSideEffects());
        }
        if (medicine.getRecipeRequired() != null) {
            m.setRecipeRequired(medicine.getRecipeRequired());
        }
        m.setDailyIntake(medicine.getDailyIntake());
        if (medicine.getAdditionalNotes() != null) {
            m.setAdditionalNotes(medicine.getAdditionalNotes());
        }

        if (medicine.getMedicineForm() != null) {
            var mf = medicineFormRepository.findByName(medicine.getMedicineForm().getName());
            mf.ifPresent(m::setMedicineForm);
        } else {
            throw new CustomException("Please set medicine form");
        }
        if (medicine.getMedicineType() != null) {
            var mt = medicineTypeRepository.findByName(medicine.getMedicineType().getName());
            mt.ifPresent(m::setMedicineType);
        } else {
            throw new CustomException("Please set medicine type");
        }
        if (medicine.getManufacturer() != null) {
            var man = manufacturerRepository.findByName(medicine.getManufacturer().getName());
            man.ifPresent(m::setManufacturer);
        } else {
            throw new CustomException("Please set manufacturer");
        }

        var tempList = new ArrayList<Medicine>();
        for (var ameddto: medicine.getAlternativeMedicine()) {
            var amed = medicineRepository.findByMedicineCode(ameddto.getCode());
            tempList.add(amed);
        }
        m.setAlternativeMedicine(tempList);
        medicineRepository.save(m);
        return true;
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
            document.addTitle(medicine.getName());
            document.add(nameParagraph);
            document.add(Chunk.NEWLINE);
            document.add(generateParagraph("1. Medicine code", medicine.getCode()));
            document.add(generateParagraph("2. Medicine name", medicine.getName()));
            document.add(generateParagraph("3. Manufacturer", medicine.getManufacturer().getName()));
            document.add(generateParagraph("4. Active ingredients", medicine.getContent()));
            document.add(generateParagraph("5. Side effects", medicine.getSideEffects()));
            document.add(generateParagraph("6. Additional notes", medicine.getAdditionalNotes()));
            document.add(generateParagraph("7. Medicine type", medicine.getMedicineType().getName()));
            document.add(generateParagraph("8. Medicine form", medicine.getMedicineForm().getName()));
            document.add(generateParagraph("9. Daily intake", String.valueOf(medicine.getDailyIntake())));
            document.add(generateParagraph("10. Average grade", String.valueOf(medicine.getAvgGrade())));
            document.add(generateAlternativesParagraph("11. Alternative medicine", medicine));
            document.close();

        } catch (DocumentException ex) {
            System.err.println("ERROR. PDF file not generated");
            return null;
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public List<Medicine> getReceivedMedicinesByPatientId(Long id) {
        Patient patient = patientRepository.findByIdFetchReceivedMedicines(id);
        if (patient == null) return null;

        List<Medicine> chosenMedicines = new ArrayList<>();

        for (MedicineReservation m : patient.getMedicineReservation()) {
            if (!m.getState().equals(ReservationState.RECEIVED)) continue;
            addMedicine(chosenMedicines, m.getMedicineItem().getMedicine());
        }

        List<ERecipeDTO> ePrescriptions = eRecipeService.getEPrescriptionsByPatientId(id);
        if (ePrescriptions != null) {
            for (ERecipeDTO dto : ePrescriptions) {
                if (dto.geteRecipeItems() != null) {
                    for (ERecipeItem item : dto.geteRecipeItems()) {
                        addMedicine(chosenMedicines, medicineRepository.findByMedicineCode(item.getMedicineCode()));
                    }
                }
            }
        }

        return chosenMedicines;
    }

    @Override
    public List<Medicine> filterMedicine(String searchParams) {
        List<SearchCriteria> sc = new ArrayList<>();
        if (searchParams != null && !searchParams.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\w+?)([:<>])([\\w ]*),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(searchParams + ",");
            while (matcher.find()) {
                sc.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        } else {
            return new ArrayList<>();
        }

        return medicineRepository.filterMedicine(sc);
    }

    private List<Medicine> addMedicine(List<Medicine> medicines, Medicine m) {

        if (medicines.size() == 0) {
            medicines.add(m);
            return medicines;
        }

        for (Medicine m1 : medicines) {
            if (m1.getId().equals(m.getId())) {
                return medicines;
            }
        }

        medicines.add(m);
        return medicines;

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
