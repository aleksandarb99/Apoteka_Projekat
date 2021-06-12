package com.team11.PharmacyProject.dto.worker;

import com.team11.PharmacyProject.workDay.WorkDay;

import java.util.ArrayList;
import java.util.List;

public class WorktimeDTO {

    private List<HolidayStartEndDTO> holidays;

    private List<WorkDay> workDayList;

    public WorktimeDTO() {
        this.holidays = new ArrayList<>();
        this.workDayList = new ArrayList<>();
    }

    public WorktimeDTO(List<HolidayStartEndDTO> holidays, List<WorkDay> workDayList) {
        this.holidays = holidays;
        this.workDayList = workDayList;
    }

    public List<HolidayStartEndDTO> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<HolidayStartEndDTO> holidays) {
        this.holidays = holidays;
    }

    public List<WorkDay> getWorkDayList() {
        return workDayList;
    }

    public void setWorkDayList(List<WorkDay> workDayList) {
        this.workDayList = workDayList;
    }
}
