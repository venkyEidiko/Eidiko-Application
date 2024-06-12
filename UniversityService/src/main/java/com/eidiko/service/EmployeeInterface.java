package com.eidiko.service;

import com.eidiko.entity.Employee;
import com.eidiko.entity.Roles;
import com.eidiko.exception_handler.UserNotFound;

public interface EmployeeInterface {
	
	public Employee saveEmployee(Employee employee);
	
	public Employee updateEmployee(int employeeId,Employee employee)throws UserNotFound;
	
    
}
