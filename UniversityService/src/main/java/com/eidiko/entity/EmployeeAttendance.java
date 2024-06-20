package com.eidiko.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAttendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long employeeId;
	private String empName;
	private LocalDate date;
	private LocalDateTime inTime;
	private LocalDateTime outTime;
	private String effectiveHours;
	private String grossHours;
	private String status;
	private String workingDayStatus;
  
                 
    

	
	
	/*public boolean isOnTime() {
	    LocalTime[] startTimes = {
	        LocalTime.of(10, 0, 0),
	        LocalTime.of(14, 0, 0),
	        LocalTime.of(21, 0, 0)
	    };

	    LocalTime inTimeLocalTime = this.inTime.toLocalTime();

	    // Check if inTimeLocalTime is strictly before any of the specified start times
	    for (LocalTime startTime : startTimes) {
	        if (inTimeLocalTime.isBefore(startTime)) {
	            return true;
	        }
	    }
	    return false;
	}
*/
}
