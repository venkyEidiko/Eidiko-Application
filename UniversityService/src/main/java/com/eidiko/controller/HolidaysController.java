package com.eidiko.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.entity.Holidays;
import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.service.HolidayService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class HolidaysController {

	@Autowired
	private HolidayService holidayService;
	@PostMapping("/saveHolidays")
    public ResponseEntity<ResponseModel<Object>> saveHolidays(@RequestParam("file") MultipartFile file,
                                  @RequestParam("description") String description,
                                  @RequestParam("fulldate") LocalDate dateOfHoliday) throws Exception {

		try {
			String saveHolidays = holidayService.saveHolidays(file, description, dateOfHoliday);

			return new CommonResponse<>().prepareSuccessResponseObject(saveHolidays);

		} catch (BadRequestException e) {

			return new CommonResponse<>().handleBadRequestException(e);
		}

		
	}
	
	@GetMapping("/getAllHolidays")
	public ResponseEntity<ResponseModel<Object>>getAllHolidays() throws UserNotFoundException
	{
		
		List<Holidays> allHolidays = holidayService.getAllHolidays();
		
		
		return new CommonResponse<>().prepareSuccessResponseObject(allHolidays);
		
		
	}

}
