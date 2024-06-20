package com.eidiko.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

import com.eidiko.entity.EmployeeAttendance;

import lombok.Data;

@Data
public class EmployeeAttendanceDTO {
	
	
	private String empName;
    private Long employeeId;
    private LocalDate date;
    private LocalDateTime inTime;
    private LocalDateTime outTime;
    private String effectiveHours;
    private String grossHours;
    private String status;
    private String workingDayStatus;
    private String dayOfWeek;
  
    
    public EmployeeAttendanceDTO(EmployeeAttendance employeeAttendance) {
        this.empName = employeeAttendance.getEmpName();
        this.employeeId = employeeAttendance.getEmployeeId();
        this.date = employeeAttendance.getDate();
        this.inTime = employeeAttendance.getInTime();
        this.outTime = employeeAttendance.getOutTime();
        this.effectiveHours = employeeAttendance.getEffectiveHours();
        this.grossHours = employeeAttendance.getGrossHours();
        this.status = employeeAttendance.getStatus();
        this.workingDayStatus = employeeAttendance.getWorkingDayStatus();
//        this.dayOfWeek = formatDayOfWeek(attendance.getDate());
       
        
    }

//    private String formatDate(LocalDate date) {
//        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
//        int day = date.getDayOfMonth();
//        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
//        return String.format("%s %d, %s", month, day, dayOfWeek);
//    }
//
//    private String formatDayOfWeek(LocalDate date) {
//        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
//    }

}
