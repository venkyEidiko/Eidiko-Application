package com.eidiko.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.dto.EmpLeaveDto;
import com.eidiko.dto.LeaveSummary;
import com.eidiko.entity.EmpLeave;
import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.SaveFailureException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.service.EmpLeaveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/leave")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class EmpLeaveController {

	@Autowired
	private EmpLeaveService leaveService;
	@Autowired
	private CommonResponse<EmpLeaveDto> commonResponse;

	@PostMapping("/saveEmpLeave")
	public ResponseEntity<ResponseModel<Object>> saveEmpLeave(@RequestBody EmpLeaveDto empLeaveDto) {
		System.out.println(empLeaveDto);
		log.info("data : {}", empLeaveDto);

		EmpLeaveDto saveEmpLeaveDto = leaveService.saveEmpLeave(empLeaveDto);
		log.info("Save the employee leave", empLeaveDto);
		if (empLeaveDto != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(saveEmpLeaveDto);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}

	}

	@PutMapping("/updateLeaveByEmployee/{leaveId}")
	public ResponseEntity<ResponseModel<Object>> updateByIdByEmployee(@RequestBody EmpLeaveDto empLeaveDto,
			@PathVariable long leaveId) {
		EmpLeaveDto savedEmpLeaveDto = leaveService.updateLeaveByEmployee(leaveId, empLeaveDto);
		log.info("Saved EmpLeaveDto Class : ", savedEmpLeaveDto);

		if (savedEmpLeaveDto != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(savedEmpLeaveDto);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}
	}

	@PutMapping("/updateLeaveByApprover/{leaveId}/{actionTakenBy}")
	public ResponseEntity<ResponseModel<Object>> updateByIdByApprover(@RequestBody EmpLeaveDto empLeaveDto,
			@PathVariable long leaveId, @PathVariable String actionTakenBy) {
		EmpLeaveDto savedEmpLeaveDto = leaveService.updateLeaveByApprover(leaveId, empLeaveDto, actionTakenBy);
		log.info("Saved EmpLeaveDto Class : ", savedEmpLeaveDto);

		if (savedEmpLeaveDto != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(savedEmpLeaveDto);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}
	}

	@GetMapping("/getLeaveById/{leaveId}")
	public ResponseEntity<ResponseModel<Object>> getEmpLeaveById(@PathVariable Long leaveId) {
		EmpLeaveDto empLeaveDto = leaveService.getEmpLeaveById(leaveId);
		if (empLeaveDto != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(empLeaveDto);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}
	}

	@GetMapping("/getAllEmpLeave")
	public ResponseEntity<ResponseModel<Object>> getAllEmpLeave(@RequestParam("employeeId") Long employeeId,
			@RequestParam("status") String status, @RequestParam("leaveType") String leaveType,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber) {
		List<EmpLeaveDto> empLeaveDto = leaveService.getAllEmpLeaveByEmpId(pageNumber, pageSize, employeeId);
		if (empLeaveDto != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(empLeaveDto);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}

	}

	@GetMapping("/getEmpLeaveSummaryByEmpId/{employeeId}")
	public ResponseEntity<ResponseModel<Object>> getEmpLeaveSummaryByEmpId(@PathVariable Long employeeId) {
		log.info("empId {}", employeeId);
		List<LeaveSummary> leaveSummary = leaveService.getEmpLeaveSummaryByEmpId(employeeId);

		if (leaveSummary != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(leaveSummary);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}
	}

	@GetMapping("/getSortLeaveType")
	public Page<EmpLeave> getLeavesByTypesAndStatuses(
			@RequestParam(value = "leaveTypes", required = false) List<String> leaveTypes,
			@RequestParam(value = "statuses", required = false) List<String> statuses,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size,
			@RequestParam(value = "employeeId") Long employeeId) {

		Pageable pageable = PageRequest.of(page, size);
		return leaveService.findByLeaveTypesAndStatuses(employeeId, leaveTypes, statuses, pageable);

	}


		//this api is used to get the details who are onleave today
	@GetMapping("/empOnLeaveToday")
	public ResponseEntity<?> getEmployeesOnLeaveToday() {
		List<EmpLeaveDto> employeeDetails = leaveService.getEmployeesOnLeaveToday();

		if (employeeDetails.isEmpty()) {
			return commonResponse.prepareFailedResponse("No employees on leave today.");
		} else {
			return commonResponse.prepareSuccessResponseObject(employeeDetails);
		}
	}

	//this api is used to get the details by their request type
	@GetMapping("/getEmployeeDetailsByRequestType")
	public ResponseEntity<?> getEmployeeDetailsByRequestType(@RequestParam String leaveType) {
		List<EmpLeaveDto> employeeDetails = leaveService.getEmployeeDetailsByRequestType(leaveType);

		if (employeeDetails.isEmpty()) {
			return commonResponse.prepareFailedResponse("No employees found with leave type: " + leaveType);
		} else {
			return commonResponse.prepareSuccessResponseObject(employeeDetails);
		}
	}

	@GetMapping("/searchByleaveKeyword/{keywords}/{employeeId}")
	public ResponseEntity<ResponseModel<Object>> searchEmployeeByKeyword(
			@PathVariable(value = "keywords") String keywords,
			
			@PathVariable(value = "employeeId") Long employeeId
			
			) throws SaveFailureException, UserNotFoundException {

		log.info("Search by keyword {}", keywords);

		return new CommonResponse<>().prepareSuccessResponseObject(leaveService.searchByKeyword(keywords,employeeId));

	}

	//update leave status
	@PutMapping("/updateEmployeeLeaveStatus/{empId}")
	public ResponseEntity<ResponseModel<Object>> updateEmployeeLeaveStatus(@PathVariable("empId") Long empId
			,@RequestBody EmpLeave empLeave ) throws UserNotFoundException{

		log.info("updateEmployeeLeaveStatus {}",empLeave);
		log.info("updateEmployeeLeaveStatus {}",empId);
		String updateEmployee = leaveService.updateEmployeeLeave(empId, empLeave);

		if (updateEmployee != null) {

			return new CommonResponse<>().prepareSuccessResponseObject(updateEmployee);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Failed to Fetch");
		}
	}
	@GetMapping("getPendingLeaveByNotifiedEmployee/{empId}")
	public ResponseEntity<ResponseModel<Object>> getPendingLeaveByNotifiedEmployee(@PathVariable("empId") Long empId
	) throws UserNotFoundException{

		List<EmpLeaveDto>empLeaveDtos = leaveService.getPendingLeaveByNotifiedEmployee(empId);

		if (empLeaveDtos != null) {

			return new CommonResponse<>().prepareSuccessResponseObject(empLeaveDtos);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Failed to Fetch");
		}
	}


}