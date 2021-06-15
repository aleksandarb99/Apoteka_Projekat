package com.team11.PharmacyProject.medicineFeatures.manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> getAllManufacturers();

    void addNew(Manufacturer mt);
}
