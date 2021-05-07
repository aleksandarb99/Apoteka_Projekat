package com.team11.PharmacyProject.therapyPrescription;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;

import javax.persistence.*;

@Entity
public class TherapyPrescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "medicine_reservation_id")
    public MedicineReservation medicineReservation;

    @Column(name = "therapy_length", nullable = false)
    private int duration;

    public TherapyPrescription() {
    }

    public TherapyPrescription(MedicineReservation medicineReservation, int duration) {
        this.medicineReservation = medicineReservation;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public MedicineReservation getMedicineReservation() {
        return medicineReservation;
    }

    public void setMedicineReservation(MedicineReservation medicineReservation) {
        this.medicineReservation = medicineReservation;
    }
}
