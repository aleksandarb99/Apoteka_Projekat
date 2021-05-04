package com.team11.PharmacyProject.dto.therapyPrescription;

import java.util.List;

public class TherapyDTO {
    Long apptId;
    List<TherapyPresriptionDTO> medicineList;
    String info;

    public TherapyDTO() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getApptId() {
        return apptId;
    }

    public void setApptId(Long apptId) {
        this.apptId = apptId;
    }

    public List<TherapyPresriptionDTO> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<TherapyPresriptionDTO> medicineList) {
        this.medicineList = medicineList;
    }
}
