package com.eidiko.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.dto.AvgHoursAndOnTimeArrivalDto;
import com.eidiko.dto.EmployeeAttendanceDTO;
import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.FileExtensionNotFound;
import com.eidiko.exception_handler.FormatMismatchException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.EmpAttendanceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class EmpAttendanceControllor {

	@Autowired
	private EmpAttendanceService empAttendanceService;

	@PostMapping("/attendanceSheet")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file)
			throws IOException, FileExtensionNotFound, ParseException {
		log.info("Attachment file name  : {}", file.getOriginalFilename());

		try {
			String save = empAttendanceService.save(file);

			if (save != null) {
				return new CommonResponse<>().prepareSuccessResponseObject(save);
			} else {
				return new CommonResponse<>().prepareErrorResponseObject(save, HttpStatus.NOT_FOUND);
			}
		} catch (FileExtensionNotFound ex) {
			return new CommonResponse<>().handleFileExtensionNotFound(ex);
		} catch (FormatMismatchException e) {

			return new CommonResponse<>().handleFormatMismatchException(e.getMessage());
		} catch (RuntimeException ex) {
			return new CommonResponse<>().prepareFailedResponse(ex.getMessage());
		}

	}

	@GetMapping("/getByEmployeeid/{empid}")
	public ResponseEntity<ResponseModel<Object>> getByEmpid(@PathVariable("empid") Long empid) {
		log.info("emp id {}", empid);
		try {
			List<EmployeeAttendanceDTO> byEmpId = empAttendanceService.getByEmployeeId(empid);
			log.info("entity {}", byEmpId);
			return new CommonResponse<>().prepareSuccessResponseObject(byEmpId);
		} catch (UserNotFoundException ex) {
			log.error("Error occurred: {}", ex.getMessage());
			return new CommonResponse<>().handleUserNotFoundException(ex);
		}
	}

	@GetMapping("/AvgPerDayAndOntimeArrival/{employeeId}/{staratDate}/{endDate}/{reportsTo}")
	public ResponseEntity<ResponseModel<Object>> getAverageHours(@PathVariable("employeeId") Long employeeId,
			@PathVariable("staratDate") LocalDate staratDate, @PathVariable("endDate") LocalDate endDate,
			
			@PathVariable("reportsTo") Long reportsTo) {

		List<AvgHoursAndOnTimeArrivalDto> averageRange;
		try {
			averageRange = empAttendanceService.AvgPerDayAndOntimeArrival(employeeId, staratDate, endDate,reportsTo);

			return new CommonResponse<>().prepareSuccessResponseObject(averageRange);
		} catch (UserNotFoundException e) {
			
			return new CommonResponse<>().handleUserNotFoundException(e);
		}

	}

}
