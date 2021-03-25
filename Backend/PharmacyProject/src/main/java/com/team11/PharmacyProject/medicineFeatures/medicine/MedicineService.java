//package com.team11.PharmacyProject.medicineFeatures.medicine;
//
//import com.team11.PharmacyProject.enums.RecipeRegime;
//import com.team11.PharmacyProject.medicineFeatures.manufacturer.Manufacturer;
//import com.team11.PharmacyProject.medicineFeatures.medicineForm.MedicineForm;
//import com.team11.PharmacyProject.medicineFeatures.medicineType.MedicineType;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class MedicineService {
//
//    public List<Medicine> getAllMedicines() {
//        Medicine medicine = new Medicine(1L, "Brufen", "1234", "Lek za glavu",
//                "Moze biti stetan", 2, RecipeRegime.REQUIRED, "", 5, 5,
//                null, new MedicineType(1L, "antibiotik"), new MedicineForm(1L, "kapsula"), new Manufacturer(1L, "Novi Sad"));
//
//        Medicine medicine2 = new Medicine(2L, "Paracetamol", "1235", "Lek za bebe",
//                "Nema", 2, RecipeRegime.REQUIRED, "", 4, 5,
//                null, new MedicineType(2L, "antibiotik"), new MedicineForm(2L, "sirup"), new Manufacturer(2L, "Beograd"));
//
//        Medicine medicine3 = new Medicine(3L, "JE jej lek", "1236", "Najbolji lek",
//                "Nema", 2, RecipeRegime.REQUIRED, "", 1, 5,
//                null, new MedicineType(2L, "antibiotik"), new MedicineForm(2L, "sirup"), new Manufacturer(2L, "Beograd"));
//
//        Medicine medicine4 = new Medicine(4L, "Svemoguci", "1237", "Lek za sve",
//                "Nema", 2, RecipeRegime.REQUIRED, "", 3, 5,
//                null, new MedicineType(2L, "antibiotik"), new MedicineForm(2L, "sirup"), new Manufacturer(2L, "Beograd"));
//
//        Medicine medicine5 = new Medicine(5L, "Mocni lek", "1238", "Lek za kerove",
//                "Nema", 2, RecipeRegime.REQUIRED, "", 5, 5,
//                null, new MedicineType(2L, "antibiotik"), new MedicineForm(2L, "sirup"), new Manufacturer(2L, "Beograd"));
//
//        Medicine medicine6 = new Medicine(6L, "anti depresiv", "1239", "Lek za macke i bla bla ha ha je je ko ko la la asjnd jasd kjansdn nista kjasndkas asid",
//                "Nema", 2, RecipeRegime.REQUIRED, "", 1, 5,
//                null, new MedicineType(2L, "antibiotik"), new MedicineForm(2L, "sirup"), new Manufacturer(2L, "Beograd"));
//        Medicine medicine7 = new Medicine(7L, "Paracetamol2", "1240", "Lek za bebe2",
//                "Nema", 2, RecipeRegime.REQUIRED, "", 4, 5,
//                null, new MedicineType(2L, "antibiotik"), new MedicineForm(2L, "sirup"), new Manufacturer(2L, "Beograd"));
//        return List.of(medicine, medicine2, medicine3, medicine4, medicine5, medicine6, medicine7);
//    }
//}
