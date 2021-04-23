package com.team11.PharmacyProject.users.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select p from Patient p where p.firstName like %?1% and p.lastName like %?2%")
    List<Patient> searchPatientsByFirstAndLastName(String firstName, String lastName);


//    "select p from Patient p inner join p.appointments a " +
//            "where a.worker.id = :workerID and a.appointmentState = 'FINISHED' "+
//            "and (:firstName is null or lower(p.firstName) like lower(concat('%', :firstName, '%'))) " +
//            "and (:lastName is null or lower(p.lastName) like lower(concat('%', :lastName, '%'))) " +
//            "and (:lowerTime is null or a.startTime > :lowerTime) " +
//            "and (:upperTime is null or a.startTime < :upperTime)"

//    , @Param("firstName") String firstName,
//    @Param("lastName") String lastName,
//    @Param("lowerTime") Long lowerTime,
//    @Param("upperTime") Long upperTime, Pageable pageable

//    countQuery = "SELECT count(p) FROM Patient p join p.appointments a " +
//            "where a.worker.id = :workerID and a.appointmentState = 'FINISHED'")

    @Query(value = "select distinct p from Patient p join fetch p.appointments a " +
            "where a.worker.id = :workerID and a.appointmentState = 'FINISHED'"+
            "and (:firstName is null or lower(p.firstName) like lower(concat('%', :firstName, '%'))) " +
            "and (:lastName is null or lower(p.lastName) like lower(concat('%', :lastName, '%'))) " +
            "and (:lowerTime is null or a.startTime > :lowerTime) " +
            "and (:upperTime is null or a.startTime < :upperTime)")
    List<Patient> getExaminedPatients(@Param("workerID") Long workerID,
                                       @Param("firstName") String firstName,
                                       @Param("lastName") String lastName,
                                       @Param("lowerTime") Long lowerTime,
                                       @Param("upperTime") Long upperTime, Sort sort);

    @Query(value = "select distinct p from Patient p join fetch p.appointments a " +
            "where a.worker.id = :workerID and a.appointmentState = 'FINISHED'")
    List<Patient> getAllExaminedPatients(@Param("workerID") Long workerID);


    @Query("SELECT p FROM Patient p JOIN FETCH p.allergies WHERE p.id = (:id)")
    Patient findByIdAndFetchAllergiesEagerly(@Param("id") Long id);

    @Query("SELECT p FROM Patient p JOIN FETCH p.medicineReservation mr WHERE p.id = (:id)")
    Patient findByIdAndFetchReservationsEagerly(@Param("id") Long id);

    @Query("SELECT p FROM Patient p JOIN FETCH p.address")
    List<Patient> getAllAndFetchAddress();
}
