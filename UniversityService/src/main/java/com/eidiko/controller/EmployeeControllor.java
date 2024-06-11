package com.eidiko.controller;
//
//<<<<<<< HEAD
//import com.eidiko.entity.*;
//import java.util.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//
//import com.eidiko.userservice.EmployeeService;

//@RestController
//public class EmployeeControllor {
//	
//	private static String mail="john.doe123@example.com";
//	@Autowired
//	private EmployeeService employeeService;
//	
//	@GetMapping("/get")
//	public List<Employee> getMethod(){
//		
//		List<Employee> empList = employeeService.getMethod();
//		
//		return empList;
//		
//	}
//	
//	@PostMapping("/save")
//	public Employee saveMethod(@RequestBody Employee emp) {
//		
//		return employeeService.saveMethod(emp);
//	}
//	@GetMapping("/getSpecific")
//	public Employee getByEmail() {
//		return employeeService.getByMailAdress(mail);
//	}
//	
//	
//=======
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.entity.Employee;
import com.eidiko.entity.ResponseModel;

import com.eidiko.entity.Roles;
import com.eidiko.exception_handler.UserNotFound;
import com.eidiko.responce.CommonResponse;
import com.eidiko.service.EmployeeInterface;
import com.eidiko.service.RoleInterface;
import com.eidiko.serviceimplementation.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeControllor {

	@Autowired
	private EmployeeInterface employeeInterface;
	
	@Autowired
	private RoleInterface roleInterface;

	@PostMapping("/save")
	public ResponseModel<Employee> saveUser(@RequestBody Employee employee) {
		Employee saveEmployee = employeeInterface.saveEmployee(employee);
		if (saveEmployee != null) {
			return new CommonResponse<Employee>().prepareSuccessResponseObject(saveEmployee).getBody();
		} else {
			ResponseModel<Employee> responseModel = new ResponseModel<>();
			responseModel.setStatus("FAILURE");
			responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
			responseModel.setResult(null); // Or provide specific error message
			return responseModel;
		}
	}
	
	
	@PutMapping("/updateEmp/{empId}")
	public Employee updateEmployee(@PathVariable("empId") int empID,@RequestBody Employee employee) throws UserNotFound{
		
		
		Employee updateEmployee = employeeInterface.updateEmployee(empID, employee);
		
		return updateEmployee;
		
	}
	
	
	@GetMapping("/getRoles")
	public List<Roles> getRoles()
	{
		
		List<Roles> allRoles = roleInterface.getAllRoles();
		
		if (allRoles!=null) {
			
			return allRoles;
			
		}
		
		return null;
		
	}
//>>>>>>> e23a7c1f3f12ad6d4b9c82c7c4c8a7abfc756b48
}
