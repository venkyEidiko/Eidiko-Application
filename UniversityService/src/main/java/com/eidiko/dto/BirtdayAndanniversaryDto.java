package com.eidiko.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
//@AllArgsConstructor
//this dto class is for providing empId,fname,lname to client when getBirthDayAnniversatyTodayList url hitting time
public class BirtdayAndanniversaryDto {
	
	private Long employeeId;
	private String firstName;
	private String lastName;
	private int noOfYearsCompletedInThisCompany;
	private LocalDate dateOfBirth;
	
	public BirtdayAndanniversaryDto(Long employeeId, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
 
       
	}
}
