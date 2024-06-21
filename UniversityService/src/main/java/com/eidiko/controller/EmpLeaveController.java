package com.eidiko.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.eidiko.entity.ResponseModel;
import com.eidiko.responce.CommonResponse;
import com.eidiko.service.EmpLeaveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/leave")
public class EmpLeaveController {

	@Autowired
	private EmpLeaveService leaveService;

	@PostMapping("/saveEmpLeave")
	public ResponseEntity<ResponseModel<Object>> saveEmpLeave(@RequestBody EmpLeaveDto empLeaveDto) {
		EmpLeaveDto saveEmpLeaveDto = leaveService.saveEmpLeave(empLeaveDto);
		log.info("Save the employee leave", empLeaveDto);
		if (empLeaveDto != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(saveEmpLeaveDto);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}

	}

//	@DeleteMapping("/deleteById/{leaveid}")
//	public ResponseEntity<ResponseModel<Object>> deleteByLeaveId(@PathVariable long leaveid) {
//		return new ResponseEntity<>(null, HttpStatus.CREATED);
//	}

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
			@PathVariable long leaveId,@PathVariable String actionTakenBy) {
		EmpLeaveDto savedEmpLeaveDto = leaveService.updateLeaveByApprover(leaveId, empLeaveDto,actionTakenBy);
		log.info("Saved EmpLeaveDto Class : ", savedEmpLeaveDto);

		if (savedEmpLeaveDto != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(savedEmpLeaveDto);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}
	}
	
	@GetMapping("/getLeaveById/{leaveId}")
	public ResponseEntity<ResponseModel<Object>> getEmpLeaveById(@PathVariable Long leaveId) {
		EmpLeaveDto empLeaveDto=leaveService.getEmpLeaveById(leaveId);
		if (empLeaveDto != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(empLeaveDto);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}
	}


	@GetMapping("/getAllEmpLeave")
	public ResponseEntity<ResponseModel<Object>> getAllEmpLeave(@RequestParam("employeeId")Long employeeId,
			@RequestParam(value = "5", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "0", defaultValue = "0", required = false) Integer pageNumber
			){
		List<EmpLeaveDto> empLeaveDto=leaveService.getAllEmpLeaveByEmpId(pageNumber, pageSize, employeeId);
		if (empLeaveDto != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(empLeaveDto);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}
		
	}
	
	@GetMapping("/getEmpLeaveSummaryByEmpId/{employeeId}")
	public ResponseEntity<ResponseModel<Object>> getEmpLeaveSummaryByEmpId(@PathVariable Long employeeId) {
		List<LeaveSummary> leaveSummary=leaveService.getEmpLeaveSummaryByEmpId(employeeId);
		if (leaveSummary != null) {
			return new CommonResponse<>().prepareSuccessResponseObject(leaveSummary);
		} else {
			return new CommonResponse<>().prepareFailedResponse("Invalid Request! Please try again.");
		}
	}
}