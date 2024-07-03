package com.eidiko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.entity.ResponseModel;
import com.eidiko.entity.ShiftRequest;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.ShiftRequestService;

@RestController
@RequestMapping("/api")
public class ShiftRequestController {

	@Autowired
	private ShiftRequestService shiftRequestService;

	// raise shift request (saves shift request in the in shftrequest db)
	@PostMapping("/saveRequest/{employeeId}")
	public ResponseEntity<ResponseModel<Object>> saveRequest(@RequestBody ShiftRequest shiftRequest,
			@PathVariable Long employeeId) {

		try {
			String saveRequest = shiftRequestService.saveRequest(employeeId, shiftRequest);

			return new CommonResponse<>().prepareSuccessResponseObject(saveRequest);
		} catch (Exception e) {
			return new CommonResponse<>().prepareFailedResponse1(e.getMessage());
		}
	}

	// updtates shift in the employee table
	@PutMapping("/approve/{employeeId}")
	public ResponseEntity<ResponseModel<Object>> approveShiftChange(@PathVariable Long employeeId) {
		try {
			String result = shiftRequestService.approveShiftChange(employeeId);
			return new CommonResponse<>().prepareSuccessResponseObject(result);
		} catch (Exception e) {
			return new CommonResponse<>().prepareFailedResponse1(e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteRequest/{employeeId}")
	public ResponseEntity<ResponseModel<Object>> deleteShiftRequest(@PathVariable Long employeeId)throws UserNotFoundException{
	
		try 
		{
			String result = shiftRequestService.deleteShiftRequestsByEmployeeId(employeeId);
			return new CommonResponse<>().prepareSuccessResponseObject(result);
		}
		catch (UserNotFoundException e) {
			return new CommonResponse<>().prepareFailedResponse1(e.getMessage()); 
		}
	}
	
}
