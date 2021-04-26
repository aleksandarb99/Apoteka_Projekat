package com.team11.PharmacyProject.dto.workplace;

import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import org.hibernate.jdbc.Work;

import java.util.List;

public class WorkplaceForWorkerProfileDTO {

    private Long id; //id of pharmacy

    private String pharmacy;

    private List<WorkDay> workDayList;

    public WorkplaceForWorkerProfileDTO(){

    }

    public WorkplaceForWorkerProfileDTO(Workplace workplace){
        this.id = workplace.getPharmacy().getId();
        this.pharmacy = workplace.getPharmacy().getName();
        this.workDayList = workplace.getWorkDays();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public List<WorkDay> getWorkDayList() {
        return workDayList;
    }

    public void setWorkDayList(List<WorkDay> workDayList) {
        this.workDayList = workDayList;
    }
}
