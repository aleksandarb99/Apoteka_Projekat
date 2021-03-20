package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.workDay.WorkDay;

import java.util.List;


public class Workplace {
   private Long id;
   private List<WorkDay> workDays;

   public Workplace() {
   }

   public Workplace(Long id, List<WorkDay> workDays) {
      this.id = id;
      this.workDays = workDays;
   }

   public Long getId() {
      return id;
   }

   public List<WorkDay> getWorkDays() {
      return workDays;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setWorkDays(List<WorkDay> workDays) {
      this.workDays = workDays;
   }

   @Override
   public String toString() {
      return "Workplace{" +
              "id=" + id +
              ", workDays=" + workDays +
              '}';
   }
}