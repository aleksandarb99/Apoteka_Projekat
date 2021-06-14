package com.team11.PharmacyProject.dto.appointment;

import com.team11.PharmacyProject.appointment.Appointment;

public class AppointmentTimeRangeDTO {
    private Long startTime;
    private Long endTime;

    public AppointmentTimeRangeDTO() {
    }

    public AppointmentTimeRangeDTO(Appointment appt) {
        this.startTime = appt.getStartTime();
        this.endTime = appt.getEndTime();
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AppointmentTimeRangeDTO)) {
            return false;
        }
        AppointmentTimeRangeDTO trange = (AppointmentTimeRangeDTO) obj;
        return this.startTime.equals(trange.getStartTime()) && this.endTime.equals(trange.getEndTime());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
