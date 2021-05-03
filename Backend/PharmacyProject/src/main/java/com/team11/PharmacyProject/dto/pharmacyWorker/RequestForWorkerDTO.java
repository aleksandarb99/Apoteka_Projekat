package com.team11.PharmacyProject.dto.pharmacyWorker;

public class RequestForWorkerDTO {

    int startHour;
    int endHour;
    boolean enable1;
    boolean enable2;
    boolean enable3;
    boolean enable4;
    boolean enable5;
    boolean enable6;
    boolean enable7;

    public RequestForWorkerDTO() {
    }

    public RequestForWorkerDTO(int startHour, int endHour, boolean enable1, boolean enable2, boolean enable3, boolean enable4, boolean enable5, boolean enable6, boolean enable7) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.enable1 = enable1;
        this.enable2 = enable2;
        this.enable3 = enable3;
        this.enable4 = enable4;
        this.enable5 = enable5;
        this.enable6 = enable6;
        this.enable7 = enable7;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public boolean isEnable1() {
        return enable1;
    }

    public void setEnable1(boolean enable1) {
        this.enable1 = enable1;
    }

    public boolean isEnable2() {
        return enable2;
    }

    public void setEnable2(boolean enable2) {
        this.enable2 = enable2;
    }

    public boolean isEnable3() {
        return enable3;
    }

    public void setEnable3(boolean enable3) {
        this.enable3 = enable3;
    }

    public boolean isEnable4() {
        return enable4;
    }

    public void setEnable4(boolean enable4) {
        this.enable4 = enable4;
    }

    public boolean isEnable5() {
        return enable5;
    }

    public void setEnable5(boolean enable5) {
        this.enable5 = enable5;
    }

    public boolean isEnable6() {
        return enable6;
    }

    public void setEnable6(boolean enable6) {
        this.enable6 = enable6;
    }

    public boolean isEnable7() {
        return enable7;
    }

    public void setEnable7(boolean enable7) {
        this.enable7 = enable7;
    }
}
