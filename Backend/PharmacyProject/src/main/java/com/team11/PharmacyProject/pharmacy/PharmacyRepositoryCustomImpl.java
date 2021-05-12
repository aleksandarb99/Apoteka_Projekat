package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class PharmacyRepositoryCustomImpl implements PharmacyRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Pharmacy> findPharmacyWithMedOnStock(List<ERecipeItem> items) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pharmacy> query = cb.createQuery(Pharmacy.class);
        Root<Pharmacy> pharmacy = query.from(Pharmacy.class);

        Path<String> medicinePath = pharmacy.join("priceList")
                .join("medicineItems")
                .join("medicine");

        Path<String> medCodePath = medicinePath.get("code");
        Path<Integer> medAmountPath = medicinePath.getParentPath().get("amount");

        List<Predicate> predicates = new ArrayList<>();
        for (var eri : items) {
            predicates.add(cb.and(cb.equal(medCodePath, eri.getMedicineCode()), cb.greaterThanOrEqualTo(medAmountPath, eri.getQuantity())) );
        }
        query.select(pharmacy)
                .distinct(true)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])))
                .groupBy(pharmacy.get("id")).having(cb.equal(cb.count(pharmacy), items.size()));
        return entityManager.createQuery(query)
                .getResultList();
    }
}
