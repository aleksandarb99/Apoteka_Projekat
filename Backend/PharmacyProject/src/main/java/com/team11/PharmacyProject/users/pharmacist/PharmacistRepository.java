package com.team11.PharmacyProject.users.pharmacist;

import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends JpaRepository<PharmacyWorker, Long> {

}
