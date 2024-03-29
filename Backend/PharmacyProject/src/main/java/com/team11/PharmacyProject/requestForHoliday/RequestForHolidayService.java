package com.team11.PharmacyProject.requestForHoliday;

import com.team11.PharmacyProject.enums.AbsenceType;

import java.util.Date;
import java.util.List;

public interface RequestForHolidayService {
    String createHolidayRequest(Long workerId, Long start, Long end, AbsenceType absenceType);

    List<RequestForHoliday> getWorkerHolidays(Long workerID);

    List<RequestForHoliday> getAcceptedWorkerHolidays(Long workerID);

    List<RequestForHoliday> getUnresolvedRequestsByPharmacy(Long pharmacyId);

    List<RequestForHoliday> getRequestForHolidayAcceptedOrPendingInFuture(Long workerID);

    boolean hasVacationInThatDateRange(Long workerID, Long start, Long end);

    boolean isWorkerOnHoliday(Long workerId, Date date);

    RequestForHoliday acceptRequest(String requestId);

    RequestForHoliday rejectRequest(String requestId, String reason);

    void cancelExpiredVacRequests();
}
