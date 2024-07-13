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
//this dto class is for through this class token,employee details are showed in the response 
public class JwtTokenReturnClass {
	
	@JsonProperty("jwtToken")
	private String jwtToken;
	

	@JsonProperty("refreshToken")
	private String refreshToken;

	@JsonProperty("employee")
	private Employee employeeDto;
	
	
}
