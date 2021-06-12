package com.team11.PharmacyProject.requestForHoliday;

import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestForHolidayRepository  extends JpaRepository<RequestForHoliday, Long> {

    @Query("select case when count(r)> 0 then true else false end from RequestForHoliday r where " +
            "r.requestState <> 'CANCELLED' and " +
            "r.pharmacyWorker.id = ?1 and ((r.startDate >= ?2 and r.startDate <= ?3) or (r.endDate >= ?2 and r.endDate <= ?3) or " +
            " (r.startDate >= ?2 and r.endDate <= ?3) or (r.startDate <= ?2 and r.endDate >= ?3)) ")
    boolean hasVacationInThatDateRange(Long workerID, Long vacationStartTime, Long vacationEndTime);

    @Query("select r from RequestForHoliday r where r.pharmacyWorker.id = ?1")
    List<RequestForHoliday> getRequestsFromUser(Long workerID);

    @Query("select r from RequestForHoliday r where r.pharmacyWorker.id = ?1 and r.requestState='ACCEPTED'")
    List<RequestForHoliday> getAcceptedRequestsFromUser(Long workerID);

    @Query("select r from RequestForHoliday r join fetch r.pharmacyWorker pw where r.pharmacyWorker.id = ?1 and r.requestState='PENDING' and r.startDate > ?2")
    List<RequestForHoliday> getUnresolvedRequestsByWorker(Long workerID, Long currentTime);

    @Query("select r from RequestForHoliday r join fetch r.pharmacyWorker pw where r.id = ?1")
    RequestForHoliday findOneWithWorker(Long id);

    @Query("select r from RequestForHoliday r where r.pharmacyWorker.id = ?1 and r.requestState='ACCEPTED' and r.startDate < ?2 and r.endDate > ?2")
    List<RequestForHoliday> findOneWithWorkerAndCheckIsHeOnHoliday(Long workerId, long currentTime);

    @Query("select r from RequestForHoliday r where r.pharmacyWorker.id = ?1 and (r.requestState='ACCEPTED' or r.requestState='PENDING') " +
            "and (r.startDate > ?2 or (r.startDate < ?2 and r.endDate > ?2))")
    List<RequestForHoliday> getRequestForHolidayAcceptedOrPendingInFuture(Long workerID, Long currentTime);

    @Query("select r from RequestForHoliday r where r.startDate < ?1 and r.requestState='PENDING'")
    List<RequestForHoliday> getExpiredHolidays(Long currentTime);
}
