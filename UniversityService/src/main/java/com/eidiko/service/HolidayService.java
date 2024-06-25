package com.eidiko.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eidiko.entity.Holidays;

public interface HolidayService {
	
	
	
	
	public String saveHolidays(MultipartFile file ,String description,LocalDate dateOFHoliday) throws IOException ;

	public List<Holidays>getAllHolidays();
	
}
