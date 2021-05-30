package com.team11.PharmacyProject.supplierItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface SupplierItemRepository extends JpaRepository<SupplierItem, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT si FROM SupplierItem si WHERE si.supplier.id = ?1 and si.medicine.code = ?2 and si.amount >= ?3")
    Optional<SupplierItem> getSupplierItemBySupplierIdCodeAmount(Long supplierId, String medicineCode, int amount);

    @Query("SELECT si FROM SupplierItem si WHERE si.supplier.id = ?1 and si.medicine.id = ?2")
    Optional<SupplierItem> getSupplierItemBySupplierIdCode(Long id, long medicineId);
}
