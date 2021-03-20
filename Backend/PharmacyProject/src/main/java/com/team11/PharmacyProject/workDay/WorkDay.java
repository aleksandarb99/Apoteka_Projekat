package com.team11.PharmacyProject.workDay;

import com.team11.PharmacyProject.enums.Weekday;

import java.time.LocalDate;

public class WorkDay {
   private Long id;
   private Weekday weekday;
   private int startTime;
   private int endTime;

   public WorkDay(Long id, Weekday weekday, int startTime, int endTime) {
      this.id = id;
      this.weekday = weekday;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   public WorkDay() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Weekday getWeekday() {
      return weekday;
   }

   public void setWeekday(Weekday weekday) {
      this.weekday = weekday;
   }

   public int getStartTime() {
      return startTime;
   }

   public void setStartTime(int startTime) {
      this.startTime = startTime;
   }

   public int getEndTime() {
      return endTime;
   }

   public void setEndTime(int endTime) {
      this.endTime = endTime;
   }
}