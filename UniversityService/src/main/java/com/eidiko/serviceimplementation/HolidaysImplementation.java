package com.eidiko.serviceimplementation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.entity.Holidays;
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.repository.HolidaysRepository;
import com.eidiko.service.HolidayService;

@Service
public class HolidaysImplementation implements HolidayService {

	@Autowired
	private HolidaysRepository holidaysRepository;


	@Override
	public String saveHolidays(MultipartFile file, String description, LocalDate dateOFHoliday) throws IOException {

		Holidays holidays = new Holidays();
     
		holidays.setDateOfHoliday(dateOFHoliday);
		holidays.setFestivalImage(file.getBytes());
		holidays.setDescription(description);
		holidays.setImageName(file.getOriginalFilename());

		Holidays save = holidaysRepository.save(holidays);

		if (save != null && save.getId() != 0) {
			return "200";
		} else {
			throw new BadRequestException("Failed to create User");
		}

	}


	@Override
	public List<Holidays> getAllHolidays() {

		
		List<Holidays> all = holidaysRepository.findAll();
		
		if (all.isEmpty()) {
			
			throw new UsernameNotFoundException("There is no record....!");
			
		}
		
		return all;
	}

}
