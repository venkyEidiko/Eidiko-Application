package com.eidiko.service;

import com.eidiko.entity.Employee;
//import com.eidiko.entity.Roles;
import com.eidiko.exception_handler.SaveFailureException;
import com.eidiko.exception_handler.UserNotFoundException;

public interface EmployeeInterface {
	
	public String saveEmployee(Employee employee)throws SaveFailureException;
	
	public String updateEmployee(int employeeId,Employee employee)throws UserNotFoundException,SaveFailureException;
	
	
    
}