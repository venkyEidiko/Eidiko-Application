package com.eidiko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//this dto class is for providing empId,fname,lname to client when getBirthDayAnniversatyTodayList url hitting time
public class BirtdayAndanniversaryDto {
	
	private Long employeeId;
	private String firstName;
	private String lastName;

}
