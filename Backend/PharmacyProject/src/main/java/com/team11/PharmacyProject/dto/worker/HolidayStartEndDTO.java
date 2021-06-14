package com.team11.PharmacyProject.dto.worker;

import com.team11.PharmacyProject.requestForHoliday.RequestForHoliday;

public class HolidayStartEndDTO {
    private Long startTime;
    private Long endTime;

    public HolidayStartEndDTO() {
    }

    public HolidayStartEndDTO(Long startTime, Long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public HolidayStartEndDTO(RequestForHoliday req) {
        this.startTime = req.getStartDate();
        this.endTime = req.getEndDate();
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
}
