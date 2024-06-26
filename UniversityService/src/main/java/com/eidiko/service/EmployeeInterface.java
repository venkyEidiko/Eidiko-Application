package com.eidiko.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eidiko.dto.BirtdayAndanniversaryDto;
import com.eidiko.entity.Employee;
import com.eidiko.exception_handler.UserNotFoundException;

public interface EmployeeInterface {
	
	public String saveEmployee(Employee employee);
	

	
	public String updateEmployee(Long employeeId,Employee employee) throws UserNotFoundException;
	
	public String updateEmployeeContactDetails(Long empLoyeeId,Employee employee)throws UserNotFoundException;
	
	public String updateEmployeePrimaryDetails(Long empLoyeeId,Employee employee)throws UserNotFoundException;
	
	public String updateEmployeeJobDetails(Long empLoyeeId,Employee employee)throws UserNotFoundException;
	
	public String updateEmployeeTimeDetails(Long empLoyeeId,Employee employee)throws UserNotFoundException;
	
	public String updateEmployeeOrganizationDetails(Long empLoyeeId,Employee employee)throws UserNotFoundException;

	
	public Optional<List<Employee>> searchByKeywords(String keywords);
    


	Map<String, List<BirtdayAndanniversaryDto>> bithDayMethod(LocalDate date);
} 

