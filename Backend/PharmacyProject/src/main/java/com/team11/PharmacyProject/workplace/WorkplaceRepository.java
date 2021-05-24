package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkplaceRepository extends CrudRepository<Workplace, Long> {

    @Query("SELECT u FROM Workplace u WHERE u.pharmacy.id = ?1 AND (u.worker.role.name = 'DERMATOLOGIST' OR u.worker.role.name = 'PHARMACIST')")
    Iterable<Workplace> getWorkplacesByPharmacyId(Long pharmacyId);

    @Query("SELECT u FROM Workplace u WHERE u.worker.role.name = 'DERMATOLOGIST' OR u.worker.role.name = 'PHARMACIST'")
    Iterable<Workplace> getWorkplacesWhichAreWorkers();

    @Query("SELECT u FROM Workplace u WHERE u.pharmacy.id = ?1 AND u.worker.id= ?2")
    Iterable<Workplace> getWorkplacesByPharmacyIdAndWorkerId(Long pharmacyId, Long workerID);

    @Query("SELECT w FROM Workplace w join fetch w.pharmacy WHERE w.worker.id = ?1")
    List<Workplace> getWorkplacesOfWorker(Long workerID);

    @Query("SELECT u FROM Workplace u join fetch u.worker WHERE u.id = ?1")
    Workplace findByIdWithWorker(Long workplaceId);

    @Query("select p from Workplace p join fetch p.worker where lower(p.worker.firstName) like lower(concat('%', ?1, '%')) or lower(p.worker.lastName) like lower(concat('%', ?1, '%'))")
    List<Workplace> searchWorkplacesByNameOrSurnameOfWorker(String searchValue);

    @Query("select p from Workplace p join fetch p.worker where p.pharmacy.id = ?2 and (lower(p.worker.firstName) like lower(concat('%', ?1, '%')) or lower(p.worker.lastName) like lower(concat('%', ?1, '%')))")
    List<Workplace> searchWorkplacesInPharmacyByNameOrSurnameOfWorker(String searchValue, Long pharmacyId);

    @Query("SELECT u FROM Workplace u WHERE u.worker.id = ?1 and u.pharmacy.id = ?2")
    Workplace getWorkplaceOfDermatologist(Long workerID, Long pharmacyID);
}

