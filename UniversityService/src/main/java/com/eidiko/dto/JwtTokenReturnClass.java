package com.eidiko.dto;

import com.eidiko.entity.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenReturnClass {
	
	@JsonProperty("jwtToken")
	private String jwtToken;

	@JsonProperty("employee")
//    private Employee employee;
	
	private EmployeeDto employeeDto;
}
