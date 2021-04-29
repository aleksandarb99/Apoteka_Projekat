package com.team11.PharmacyProject.dto.requestForHoliday;

import com.team11.PharmacyProject.enums.AbsenceRequestState;
import com.team11.PharmacyProject.enums.AbsenceType;
import com.team11.PharmacyProject.requestForHoliday.RequestForHoliday;

public class RequestForHolidayWithWorkerDetailsDTO {

    private String id;  //string da bi se dodala rec request na sve; zbog frontend kalendara, da se ne bi mesali
                        //id-ovi request-ova sa id-ovima appointmenta

    private Long start;

    private Long end;

    private AbsenceRequestState requestState;

    private AbsenceType absenceType;

    private String declineText;

    private String calendarType = "vacationReq";

    private String workerDetails;


    public RequestForHolidayWithWorkerDetailsDTO() {
    }

    public RequestForHolidayWithWorkerDetailsDTO(RequestForHoliday request) {
        this.id = "request" + request.getId();
        this.start = request.getStartDate();
        this.end = request.getEndDate();
        this.requestState = request.getRequestState();
        this.absenceType = request.getAbsenceType();
        this.workerDetails = request.getPharmacyWorker().getFirstName() + " " +  request.getPharmacyWorker().getLastName();
        if (request.getDeclineText() == null)
            this.declineText = "";
        else
            this.declineText = request.getDeclineText();
    }

    public String getWorkerDetails() {
        return workerDetails;
    }

    public void setWorkerDetails(String workerDetails) {
        this.workerDetails = workerDetails;
    }

    public String getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(String calendarType) {
        this.calendarType = calendarType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public AbsenceRequestState getRequestState() {
        return requestState;
    }

    public void setRequestState(AbsenceRequestState requestState) {
        this.requestState = requestState;
    }

    public AbsenceType getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(AbsenceType absenceType) {
        this.absenceType = absenceType;
    }

    public String getDeclineText() {
        return declineText;
    }

    public void setDeclineText(String declineText) {
        this.declineText = declineText;
    }
}
