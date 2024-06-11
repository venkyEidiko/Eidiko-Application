package com.eidiko.controller;

import com.eidiko.entity.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.eidiko.userservice.EmployeeService;

@RestController
public class EmployeeControllor {
	
	private static String mail="john.doe123@example.com";
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/get")
	public List<Employee> getMethod(){
		
		List<Employee> empList = employeeService.getMethod();
		
		return empList;
		
	}
	
	@PostMapping("/save")
	public Employee saveMethod(@RequestBody Employee emp) {
		
		return employeeService.saveMethod(emp);
	}
	@GetMapping("/getSpecific")
	public Employee getByEmail() {
		return employeeService.getByMailAdress(mail);
	}
	
	
}
