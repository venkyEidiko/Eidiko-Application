package com.eidiko.serviceimplementation;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.dto.AvgHourDto;
import com.eidiko.dto.EmployeeAttendanceDTO;
import com.eidiko.entity.EmployeeAttendance;
import com.eidiko.excelreader.ExcelReader;
import com.eidiko.exception_handler.FileExtensionNotFound;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.repository.EmpAttendanceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmpAttendanceService {

	@Autowired
	private EmpAttendanceRepository attendanceRepository;

	@Autowired
	private ExcelReader excelReader;

	public String save(MultipartFile file) throws IOException, FileExtensionNotFound, ParseException {
		if (!ExcelReader.checkExcelFormat(file)) {
			throw new FileExtensionNotFound("File extension not found. Please use .XLSX");
		}

		List<EmployeeAttendance> saveAttendanceData = excelReader.saveAttendanceData(file);

		log.info("Save data: {}", saveAttendanceData);

		Iterable<EmployeeAttendance> saveAll = attendanceRepository.saveAll(saveAttendanceData);

		if (saveAll != null) {
			return "Attendance record has been created...!";
		} else {
			return "Attendance record has not been created...X";
		}
	}

	public List<EmployeeAttendanceDTO> getByEmpId(Long empid) throws UserNotFoundException {

		List<EmployeeAttendanceDTO> dtoList = new ArrayList<>();
		List<EmployeeAttendance> findByEmpId = attendanceRepository.findByEmployeeId(empid);

		if (findByEmpId == null || findByEmpId.isEmpty()) {

			throw new UserNotFoundException("This user not found :" + empid);
		}
		for (EmployeeAttendance empAttendance : findByEmpId) {
			EmployeeAttendanceDTO dto = new EmployeeAttendanceDTO(empAttendance);

			dtoList.add(dto);
		}

		return dtoList;

	}

	public String calculateAverageHoursPerDay(String totalEffectiveHours, int workingDays) {
		String[] timeParts = totalEffectiveHours.split(":");
		int totalHours = Integer.parseInt(timeParts[0]);
		int totalMinutes = Integer.parseInt(timeParts[1]);
		int totalTimeInMinutes = totalHours * 60 + totalMinutes;
		int averageMinutesPerDay = totalTimeInMinutes / workingDays;
		int averageHours = averageMinutesPerDay / 60;
		int remainingMinutes = averageMinutesPerDay % 60;
		String format = String.format("%02dH:%02dM", averageHours, remainingMinutes);
		return format;
	}

	/*public List<AvgHourDto> getAverageRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
		List<AvgHourDto> dtoList = new ArrayList<>();

		List<EmployeeAttendance> byEmployeeIdAndDateBetween = attendanceRepository
				.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate);
		log.info("get {}", byEmployeeIdAndDateBetween.toString());
		long onTimeCount = byEmployeeIdAndDateBetween.stream().filter(EmployeeAttendance::isOnTime).count();
		long totalDays = byEmployeeIdAndDateBetween.size();
		System.out.println(onTimeCount + " | " + totalDays);
//		double o1 = onTimeCount;
//		double o2 = totalDays;
//		double d = o1 / o2 * 100;
//		System.out.println(d);
		for (EmployeeAttendance employeeAttendance : byEmployeeIdAndDateBetween) {

			LocalDateTime inTime = employeeAttendance.getInTime();
			LocalTime localTime = inTime.toLocalTime();
			LocalTime localTime2 = employeeAttendance.getOutTime().toLocalTime();

			AvgHourDto dto = new AvgHourDto();

			dto.setInTime(localTime);
			dto.setOutTime(localTime2);
			dtoList.add(dto);
		}

		long count = dtoList.stream().filter(AvgHourDto::isOnTime).count();
		System.out.println("count " + count);

		return dtoList;

	}
	
	
	*/
	
	
	
	public List<AvgHourDto> getAverageRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
	    List<AvgHourDto> dtoList = new ArrayList<>();

	    List<EmployeeAttendance> byEmployeeIdAndDateBetween = attendanceRepository
	            .findByEmployeeIdAndDateBetween(employeeId, startDate, endDate);

	    
	    for (EmployeeAttendance employeeAttendance : byEmployeeIdAndDateBetween) {
	    	
	    	//LocalTime localTime = employeeAttendance.getInTime().toLocalTime();
	        AvgHourDto dto = new AvgHourDto();
	        dto.setInTime(employeeAttendance.getInTime().toLocalTime());
	        dto.setOutTime(employeeAttendance.getOutTime().toLocalTime());
	        dtoList.add(dto);
	    }

	    long countOnTime = dtoList.stream().filter(AvgHourDto::isOnTime).count();
	    System.out.println("Number of on-time arrivals: " + countOnTime);

	    
	    
	   

	    return dtoList;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
