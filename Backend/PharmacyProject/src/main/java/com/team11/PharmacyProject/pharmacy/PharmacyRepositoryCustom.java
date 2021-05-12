package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;

import java.util.List;

public interface PharmacyRepositoryCustom {
    List<Pharmacy> findPharmacyWithMedOnStock(List<ERecipeItem> items);
}
