package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.team11.PharmacyProject.search.SearchCriteria;

import java.util.List;

public interface MedicineRepositoryCustom {
    List<Medicine> filterMedicine(List<SearchCriteria> sc);
}
