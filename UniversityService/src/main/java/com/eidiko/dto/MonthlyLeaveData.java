package com.eidiko.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class MonthlyLeaveData {
   private double totalLeaveTaken;
   private List<LocalDate> leaveDays;
}
