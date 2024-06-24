package com.eidiko.service;

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
    
}