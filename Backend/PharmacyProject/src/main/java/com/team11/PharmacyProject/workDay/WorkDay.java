package com.team11.PharmacyProject.workDay;

import com.team11.PharmacyProject.enums.Weekday;
import com.team11.PharmacyProject.workplace.Workplace;

import javax.persistence.*;

@Entity
public class WorkDay {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.EAGER)
   private Workplace workplace;                 // Ovaj atribut sam dodao

   @Column(name = "weekday", nullable = false)
   private Weekday weekday;

   @Column(name = "startTime", nullable = false)
   private int startTime;

   @Column(name = "endTime", nullable = false)
   private int endTime;

   public WorkDay(Long id, Weekday weekday, int startTime, int endTime, Workplace workplace) {
      this.id = id;
      this.weekday = weekday;
      this.startTime = startTime;
      this.endTime = endTime;
      this.workplace = workplace;
   }

   public WorkDay() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Workplace getWorkplace() {
      return workplace;
   }

   public void setWorkplace(Workplace workplace) {
      this.workplace = workplace;
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