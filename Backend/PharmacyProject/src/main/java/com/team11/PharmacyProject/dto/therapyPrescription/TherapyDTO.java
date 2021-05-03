package com.team11.PharmacyProject.dto.therapyPrescription;

import java.util.List;

public class TherapyDTO {
    Long apptId;
    List<TherapyPresriptionDTO> medicineList;

    public TherapyDTO() {
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
