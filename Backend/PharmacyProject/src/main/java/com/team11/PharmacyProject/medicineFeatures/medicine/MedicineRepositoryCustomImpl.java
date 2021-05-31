package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.team11.PharmacyProject.search.SearchCriteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

public class MedicineRepositoryCustomImpl implements MedicineRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Medicine> filterMedicine(List<SearchCriteria> sc) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Medicine> query = cb.createQuery(Medicine.class);
        Root<Medicine> medicine = query.from(Medicine.class);

        Path<String> typePath = medicine.join("medicineType");
        Path<String> formPath = medicine.join("medicineForm");

        Predicate medicineName;
        Predicate medicineType;
        Predicate medicineForm;

        medicineName = cb.like(cb.upper(typePath.getParentPath().get("name")), "%" + sc.get(0).getValue().toString().toUpperCase() + "%");

        if (!sc.get(1).getValue().toString().isEmpty()) {
            medicineType = cb.equal(typePath.get("name"), sc.get(1).getValue().toString());
        } else {
            medicineType = cb.and();
        }

        if (!sc.get(2).getValue().toString().isEmpty()) {
            medicineForm = cb.equal(formPath.get("name"), sc.get(2).getValue().toString());
        } else {
            medicineForm = cb.and();
        }

        query.select(medicine)
                .distinct(true)
                .where(cb.and(medicineName, medicineType, medicineForm));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
