package com.team11.PharmacyProject.workDay;

import com.team11.PharmacyProject.enums.Weekday;

import javax.persistence.*;

@Entity
public class WorkDay {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "weekday", nullable = false)
   private Weekday weekday;

   @Column(name = "startTime", nullable = false)
   private int startTime;

   @Column(name = "endTime", nullable = false)
   private int endTime;

   public WorkDay() {
   }

   public WorkDay(Long id, Weekday weekday, int startTime, int endTime) {
      this.id = id;
      this.weekday = weekday;
      this.startTime = startTime;
      this.endTime = endTime;
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