package com.team11.PharmacyProject.users.supplier;

import com.team11.PharmacyProject.supplierItem.SupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("select supp from Supplier supp join fetch supp.supplierItems si join fetch si.medicine where supp.id = ?1")
    Optional<Supplier> findSupplierWithSupplierItemsId(Long id);
}
