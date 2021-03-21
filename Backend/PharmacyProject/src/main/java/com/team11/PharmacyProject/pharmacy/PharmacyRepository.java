package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.advertisement.Advertisement;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.dermatologistWorkplace.DermatologistWorkplace;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.enums.RecipeRegime;
import com.team11.PharmacyProject.enums.Weekday;
import com.team11.PharmacyProject.location.Location;
import com.team11.PharmacyProject.medicineFeatures.manufacturer.Manufacturer;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicineForm.MedicineForm;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.medicineFeatures.medicineType.MedicineType;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.users.dermatologist.Dermatologist;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PharmacyRepository {

    private List<Pharmacy> pharmacyList;

    public PharmacyRepository() {
        this.pharmacyList = new ArrayList<>();

        List<MedicineItem> medicineItemList = new ArrayList<>();

        MedicinePrice medicinePrice1 = new MedicinePrice(1L, 500, LocalDate.now(), new ArrayList<>());
        List<MedicinePrice> prices = new ArrayList<>();
        prices.add(medicinePrice1);

        Medicine medicine = new Medicine(1L, "Brufen", "1234", "Lek za glavu",
                "Moze biti stetan", 2, RecipeRegime.REQUIRED, "", 5, 5,
                null, new MedicineType(1L, "antibiotik"), new MedicineForm(1L, "kapsula"), new Manufacturer(1L, "Novi Sad"));
        MedicineItem medicineItem1 = new MedicineItem(1L, 100, prices, medicine);

        medicineItemList.add(medicineItem1);

        MedicinePrice medicinePrice2 = new MedicinePrice(2L, 1000, LocalDate.now(), new ArrayList<>());
        List<MedicinePrice> prices2 = new ArrayList<>();
        prices2.add(medicinePrice2);
        Medicine medicine2 = new Medicine(2L, "Paracetamol", "1235", "Lek za bebe",
                "Nema", 2, RecipeRegime.REQUIRED, "", 4, 5,
                null, new MedicineType(2L, "antibiotik"), new MedicineForm(2L, "sirup"), new Manufacturer(2L, "Beograd"));

        MedicineItem medicineItem2 = new MedicineItem(2L, 50, prices2, medicine2);

        medicineItemList.add(medicineItem2);

        MedicinePrice medicinePrice3 = new MedicinePrice(3L, 560, LocalDate.now(), new ArrayList<>());
        List<MedicinePrice> prices3 = new ArrayList<>();
        prices3.add(medicinePrice3);
        Medicine medicine3 = new Medicine(3L, "JE jej lek", "1236", "Najbolji lek",
                "Nema", 2, RecipeRegime.REQUIRED, "", 1, 5,
                null, new MedicineType(2L, "antibiotik"), new MedicineForm(2L, "sirup"), new Manufacturer(2L, "Beograd"));

        MedicineItem medicineItem3 = new MedicineItem(3L, 200, prices3, medicine3);

        medicineItemList.add(medicineItem2);

        ArrayList<Appointment> appointmentList = new ArrayList<>();
        Appointment appointment1 = new Appointment(1L, LocalDate.now(), LocalDate.now(), 15, AppointmentState.EMPTY, "Informacije o sastanku", 2000, AppointmentType.CHECKUP, null, null);
        Appointment appointment2 = new Appointment(2L, LocalDate.now(), LocalDate.now(), 25, AppointmentState.EMPTY, "Informacije o sastanku", 2500, AppointmentType.CHECKUP, null, null);
        appointmentList.add(appointment1);
        appointmentList.add(appointment2);

        Pharmacy pharmacy = new Pharmacy(1L, "Zelena Apoteka", "Najbolja apoteka u gradu.",
                4.2, new ArrayList<>(), new PriceList(1L, medicineItemList), appointmentList,
                new Location(22.22, 11.11), new ArrayList<>());

        pharmacyList.add(pharmacy);
    }

    public Pharmacy getPharmacyById(long id) {
        return this.pharmacyList.stream().filter((pharmacy -> pharmacy.getId() == id)).findFirst().orElse(null);
    }

    public void save(Pharmacy pharmacy) {
        pharmacyList.add(pharmacy);
    }
}
