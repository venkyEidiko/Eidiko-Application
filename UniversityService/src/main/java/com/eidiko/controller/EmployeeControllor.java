package com.eidiko.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.dto.BirtdayAndanniversaryDto;
import com.eidiko.dto.EmployeeDto;
import com.eidiko.entity.Employee;
import com.eidiko.entity.ResponseModel;
import com.eidiko.entity.Roles_Table;
//import com.eidiko.entity.Roles;
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.exception_handler.SaveFailureException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.service.EmployeeInterface;
import com.eidiko.service.RoleInterface;
import com.eidiko.serviceimplementation.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeControllor {

	// @Autowired
	private EmployeeInterface employeeInterface;

	// @Autowired
	private RoleInterface roleInterface;

//	@Autowired
	private EmployeeService employeeService;

	public EmployeeControllor(EmployeeInterface employeeInterface, RoleInterface roleInterface,
			EmployeeService employeeService) {
		super();
		this.employeeInterface = employeeInterface;
		this.roleInterface = roleInterface;
		this.employeeService = employeeService;
	}

	@GetMapping("/getRoles")
	public List<Roles_Table> getRoles() {

		List<Roles_Table> allRoles = roleInterface.getAllRoles();

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

			return new CommonResponse<>().handleBadRequestException(e.getMessage());
		}

	}
    //@PatchMapping
	// this method is main update method
	@PutMapping("/updateEmployee/{empId}")
	public ResponseEntity<ResponseModel<Object>> updateEmployee(@PathVariable("empId") Long empID,
			@RequestBody Employee employee) throws UserNotFoundException, SaveFailureException {
		log.info("updateEmployee empId :" + empID);
		log.info("updateEmployee entry object " + employee);
		String updateEmployee = employeeInterface.updateEmployee(empID, employee);
		if (updateEmployee != null) {

			return new CommonResponse<>().prepareSuccessResponseObject(updateEmployee);
		} else {
			return new CommonResponse<>().prepareFailedResponse(updateEmployee);
		}

	}

	@GetMapping("/searchByKeyword/{keywords}")
	public ResponseEntity<ResponseModel<Object>> searchEmployeeByKeyword(@PathVariable("keywords") String keywords)
			throws SaveFailureException, UserNotFoundException {

		log.info("Search by keyword {}", keywords);

		return new CommonResponse<>().prepareSuccessResponseObject(employeeInterface.searchByKeywords(keywords));

	}

	@GetMapping("/getBybyEmployeeID/{employeeID}")
	public ResponseEntity<ResponseModel<Object>> getByEmployeeId(@PathVariable("employeeID") Long employeeId) {

		System.out.println("EMployee service :" + employeeId);

		try {
			Employee byEmployeeID = employeeService.getByEmployeeId(employeeId);
			return new CommonResponse<>().prepareSuccessResponseObject(byEmployeeID);

		} catch (BadRequestException e) {

			return new CommonResponse<>().prepareFailedResponse(e.getMessage());
		}
	}

	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<ResponseModel<Object>> getByEmail(@PathVariable String email) throws UserNotFoundException {

		Employee byEmail = employeeService.getByEmail(email);
		return new CommonResponse<>().prepareSuccessResponseEmail(byEmail.getEmail());
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

	// for birthdays and anniversaries giving
	@GetMapping("/getBirthDayAnniversatyTodayList")
	public ResponseEntity<ResponseModel<Object>> birthDayDate() {

		LocalDate today = LocalDate.now();
		try {

			return new CommonResponse<>().prepareSuccessResponseObject(employeeInterface.bithDayMethod(today));
		} catch (Exception e) {
			return new CommonResponse<>().prepareErrorResponseObject("something went wrong", HttpStatus.BAD_REQUEST);
		}
	}

//This api returns both present and after 7 days birthday details
	@GetMapping("/todayAndNextSevenDaysBirthdaysList")
	public ResponseEntity<ResponseModel<Object>> getBirthdaysAndAnniversariesForTodayAndNextSevenDays() {
		try {
			Map<String, List<BirtdayAndanniversaryDto>> response = employeeService
					.getBirthdaysAndAnniversariesForTodayAndNextSevenDays();
			return new CommonResponse<>().prepareSuccessResponseObject(response);
		} catch (Exception e) {
			return new CommonResponse<>().prepareErrorResponseObject("something went wrong", HttpStatus.BAD_REQUEST);
		}
	}

	// this api returns both present and after 7 days anniversaries details
	@GetMapping("/todayAndNextSevenDaysAnniversaryList")
	public ResponseEntity<ResponseModel<Object>> getTodayAndNextDaysAnniversaries() {
		try {
			Map<String, List<BirtdayAndanniversaryDto>> response = employeeService
					.getTodayAndSevenDaysAnniversaryList();
			return new CommonResponse<>().prepareSuccessResponseObject(response);
		} catch (Exception e) {
			return new CommonResponse<>().prepareErrorResponseObject("something went wrong", HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping("/nextSevenDaysBirthdays")
	public ResponseEntity<ResponseModel<Object>> getNextSevenDaysBirthdays() {
		List<BirtdayAndanniversaryDto> employeesWithBirthdays = employeeService
				.getEmployeesWithBirthdaysNextSevenDays();

		if (employeesWithBirthdays.isEmpty()) {
			return new CommonResponse<>().prepareFailedResponse("No birthdays in the next 7 days");
		} else {
			return new CommonResponse<>().prepareSuccessResponseObject(employeesWithBirthdays);
		}
	}


	@GetMapping("/getByAllEmployeeByEmployeeRportsTo/{employeeID}")
	public ResponseEntity<ResponseModel<Object>> getByAllEmployeeByEmployeeRportsTo(
			@PathVariable("employeeID") Long employeeId) {

		try {
			List<EmployeeDto> allEmployee = employeeService.getAllEmployee(employeeId);
			return new CommonResponse<>().prepareSuccessResponseObject(allEmployee);
		} catch (BadRequestException e) {

			return new CommonResponse<>().prepareFailedResponse2(e.getMessage());
		} catch (UserNotFoundException e) {

			return new CommonResponse<>().prepareFailedResponse1(e);
		}

	}
	
	
	//this api is used for fetching new joinees and last 7 days joinees also
		@GetMapping("/newJoineesAndLast7Days")
		public ResponseEntity<ResponseModel<Object>> getNewJoinersForTodayAndLast7Days() {
			try {
				Map<String, List<Map<String, Object>>> response = employeeService.getNewJoinersForTodayAndLast7Days();
				return new CommonResponse<>().prepareSuccessResponseObject(response);
			} catch (Exception e) {
				return new CommonResponse<>().prepareErrorResponseObject("something went wrong", HttpStatus.BAD_REQUEST);
			}
		}

	//this api is used for fetching both work anniversary today and next 7 days also
	@GetMapping("/workAnniversaries")
	public ResponseEntity<ResponseModel<Object>> getWorkAnniversariesForTodayAndNextSevenDays() {
		try {
			Map<String, List<Map<String, Object>>> response = employeeService.getWorkAnniversariesForTodayAndNextSevenDays();
			return new CommonResponse<>().prepareSuccessResponseObject(response);
		} catch (Exception e) {
			return new CommonResponse<>().prepareErrorResponseObject("something went wrong", HttpStatus.BAD_REQUEST);
		}
	}






	}
