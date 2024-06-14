package com.eidiko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.eidiko.exception_handler.SaveFailureException;
import com.eidiko.exception_handler.UserNotFound;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.service.EmployeeInterface;
import com.eidiko.service.RoleInterface;
import com.eidiko.serviceimplementation.EmployeeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeControllor {

	@Autowired
	private EmployeeInterface employeeInterface;
	
	@Autowired
	private RoleInterface roleInterface;


	@Autowired
	private EmployeeService employeeService;

	
	
	
	@GetMapping("/getRoles")
	public List<Roles> getRoles()
	{
		
		List<Roles> allRoles = roleInterface.getAllRoles();
		
		if (allRoles!=null) {
			
			return allRoles;
			
		}
		
		return null;
		
	}
	
	
	@PostMapping("/save")
	public ResponseEntity<ResponseModel<Object>> saveUser(@RequestBody Employee employee) throws SaveFailureException {

		String saveEmployee = employeeInterface.saveEmployee(employee);
		if (saveEmployee != null) {

			return new CommonResponse<>().prepareSuccessResponseObject(saveEmployee);

		} else {
			return new CommonResponse<>().prepareFailedResponse(saveEmployee);
		}
		// return null;

	}

	@PutMapping("/updateEmp/{empId}")
	public ResponseEntity<ResponseModel<Object>> updateEmployee(@PathVariable("empId") int empID,
			@RequestBody Employee employee) throws UserNotFoundException, SaveFailureException {

		String updateEmployee = employeeInterface.updateEmployee(empID, employee);

		if (updateEmployee != null) {

			return new CommonResponse<>().prepareSuccessResponseObject(updateEmployee);
		} else {
			return new CommonResponse<>().prepareFailedResponse(updateEmployee);
		}

	}

	
	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<ResponseModel<Object>> getByEmail(@PathVariable String email) throws UserNotFoundException {
	  
		System.out.println("EMployee service :"+email);
		Employee byEmail = employeeService.getByEmail(email);
	    String email2 = byEmail.getEmail();
	    // Prepare the success response using the common method
	    return new CommonResponse<>().prepareSuccessResponseEmail(email2);
	}

}
