package com.eidiko.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgHourDto {

	private LocalTime inTime;
	private LocalTime outTime;

	
	public boolean isOnTime() {
	    LocalTime[] startTimes = {
	        LocalTime.of(10, 0, 0),
	        LocalTime.of(14, 0, 0),
	        LocalTime.of(21, 0, 0)
	    };

//	    LocalTime inTimeLocalTime = this.inTime.toLocalTime();

	    // Check if inTimeLocalTime is strictly before any of the specified start times
	    for (LocalTime startTime : startTimes) {
	    	System.out.println(inTime);
	        if (inTime.isBefore(startTime)) {
	            return true;
	        }
	    }
	    return false;
}


	}
