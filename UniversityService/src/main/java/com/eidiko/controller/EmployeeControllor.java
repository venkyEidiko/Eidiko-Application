package com.eidiko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.exception_handler.SaveFailureException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.service.EmployeeInterface;
import com.eidiko.service.RoleInterface;
import com.eidiko.serviceimplementation.EmployeeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EmployeeControllor {

	@Autowired
	private EmployeeInterface employeeInterface;

	@Autowired
	private RoleInterface roleInterface;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/getRoles")
	public List<Roles> getRoles() {

		List<Roles> allRoles = roleInterface.getAllRoles();

		if (allRoles != null) {
			return allRoles;
		}
		return null;

	}

	@PostMapping("/save")
	public ResponseEntity<ResponseModel<Object>> saveUser(@RequestBody Employee employee) {

		String saveEmployee;
		try {
			saveEmployee = employeeInterface.saveEmployee(employee);
			return new CommonResponse<>().prepareSuccessResponseObject(saveEmployee);
		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			return new CommonResponse<>().handleBadRequestException(e.getMessage());
		}

	}
	@GetMapping("/searchByKeyword/{keywords}")
	public ResponseEntity<ResponseModel<Object>> searchEmployeeByKeyword(
			@PathVariable("keywords") String keywords)
			throws SaveFailureException, UserNotFoundException {
		
		log.info("Search by key id {}",keywords);
		
			return new CommonResponse<>()
					.prepareSuccessResponseObject(employeeInterface.searchByKeywords(keywords));
		
		
	}

	@PutMapping("/updateEmp/{empId}")
	public ResponseEntity<ResponseModel<Object>> updateEmployee(@PathVariable("empId") Long empID,
			@RequestBody Employee employee) throws UserNotFoundException {
		return new CommonResponse<>().prepareSuccessResponseObject(employeeInterface.updateEmployee(empID, employee));
	}

	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<ResponseModel<Object>> getByEmail(@PathVariable String email) throws UserNotFoundException {

		System.out.println("EMployee service :" + email);
		Employee byEmail = employeeService.getByEmail(email);
		String email2 = byEmail.getEmail();
		// Prepare the success response using the common method
		return new CommonResponse<>().prepareSuccessResponseEmail(byEmail);
	}

	@PutMapping("/updateEmployeeContactDetailsByEmpId/{empId}")
	public ResponseEntity<ResponseModel<Object>> updateEmployeeContactDetailsByEmpId(@PathVariable("empId") Long empID,
			@RequestBody Employee employee) throws SaveFailureException, UserNotFoundException {
		return new CommonResponse<>()
				.prepareSuccessResponseObject(employeeInterface.updateEmployeeContactDetails(empID, employee));
	}

	@PutMapping("/updateEmployeePrimaryDetailsByEmpId/{empId}")
	public ResponseEntity<ResponseModel<Object>> updateEmployeePrimaryDetailsByEmpId(@PathVariable("empId") Long empID,
			@RequestBody Employee employee) throws SaveFailureException, UserNotFoundException {
		return new CommonResponse<>()
				.prepareSuccessResponseObject(employeeInterface.updateEmployeePrimaryDetails(empID, employee));
	}
	
	@PutMapping("/updateEmployeeJobDetailsByEmpId/{empId}")
	public ResponseEntity<ResponseModel<Object>> updateEmployeeJobDetailsByEmpId(@PathVariable("empId") Long empID,
			@RequestBody Employee employee) throws SaveFailureException, UserNotFoundException {
		return new CommonResponse<>()
				.prepareSuccessResponseObject(employeeInterface.updateEmployeeJobDetails(empID, employee));
	}

	@PutMapping("/updateEmployeeTimeDetailsByEmpId/{empId}")
	public ResponseEntity<ResponseModel<Object>> updateEmployeeTimeDetailsByEmpId(@PathVariable("empId") Long empID,
			@RequestBody Employee employee) throws SaveFailureException, UserNotFoundException {
		return new CommonResponse<>()
				.prepareSuccessResponseObject(employeeInterface.updateEmployeeTimeDetails(empID, employee));
	}
	
	@PutMapping("/updateEmployeeOrganizationDetailsByEmpId/{empId}")
	public ResponseEntity<ResponseModel<Object>> updateEmployeeOrganizationDetailsByEmpId(
			@PathVariable("empId") Long empID, @RequestBody Employee employee)
			throws SaveFailureException, UserNotFoundException {
		return new CommonResponse<>()
				.prepareSuccessResponseObject(employeeInterface.updateEmployeeOrganizationDetails(empID, employee));
	}

	

	

}