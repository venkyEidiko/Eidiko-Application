package com.eidiko.serviceimplementation;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.dto.AvgHoursAndOnTimeArrivalDto;
import com.eidiko.dto.EmployeeAttendanceDTO;
import com.eidiko.entity.EmployeeAttendance;
import com.eidiko.excelreader.ExcelReader;
import com.eidiko.exception_handler.FileExtensionNotFound;
import com.eidiko.exception_handler.FormatMismatchException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.repository.EmpAttendanceRepository;
import com.eidiko.repository.EmployeeRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmpAttendanceService {

	@Autowired
	private EmpAttendanceRepository attendanceRepository;
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private ExcelReader excelReader;

	public String save(MultipartFile file)
			throws IOException, FileExtensionNotFound, ParseException, FormatMismatchException {
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

	public List<EmployeeAttendanceDTO> getByEmployeeId(Long empid) throws UserNotFoundException {

		List<EmployeeAttendanceDTO> dtoList = new ArrayList<>();
		List<EmployeeAttendance> findByEmpId = attendanceRepository.findByEmployeeId(empid);

		if (findByEmpId == null || findByEmpId.isEmpty()) {

			throw new UserNotFoundException("This user not found :" + empid);
		}
		for (EmployeeAttendance empAttendance : findByEmpId) {
			EmployeeAttendanceDTO dto = new EmployeeAttendanceDTO(empAttendance);

			dtoList.add(dto);
		}
		;
		return dtoList;

	}

	public List<AvgHoursAndOnTimeArrivalDto> AvgPerDayAndOntimeArrival(Long empid, LocalDate statDate,
			LocalDate endDate, Long reportsTo) throws UserNotFoundException {

		List<EmployeeAttendance> byEmployeeId = attendanceRepository.findByEmployeeId(empid);

		if (byEmployeeId == null || byEmployeeId.isEmpty()) {

			throw new UserNotFoundException("This user not found :" + empid);
		}
		List<AvgHoursAndOnTimeArrivalDto> aDto = new ArrayList<>();

		AvgHoursAndOnTimeArrivalDto avgDto = new AvgHoursAndOnTimeArrivalDto();

		List<EmployeeAttendance> employeeAttendances = attendanceRepository.findByEmployeeIdAndDateBetween(empid,
				statDate, endDate);
		
		int count = 0;
		int size = employeeAttendances.size();
		Duration totalEffectiveDuration = Duration.ZERO;

		for (EmployeeAttendance empAttendance : employeeAttendances) {

			String effectiveHoursStr = empAttendance.getEffectiveHours();

			LocalTime effectiveHours = LocalTime.parse(effectiveHoursStr, DateTimeFormatter.ofPattern("HH:mm:ss"));

			totalEffectiveDuration = totalEffectiveDuration.plusHours(effectiveHours.getHour())
					.plusMinutes(effectiveHours.getMinute());

			String status = empAttendance.getStatus();
			System.out.println(status);
			if (status.equals("OnTime")) {

				count++;
			}

		}
		long totalHours = totalEffectiveDuration.toHours();
		long totalMinutes = totalEffectiveDuration.toMinutes() % 60;
		String totalEffectiveHours = String.format("%02d:%02d", totalHours, totalMinutes);

		String averageHoursPerDay = this.calculateAverageHoursPerDay(totalEffectiveHours, size);

		double onTimeArival = ((double) count / size) * 100;
		int p = (int) onTimeArival;
		String onTimeArrivalPer = String.valueOf(p) + " % ";

		// Finding team average arrival % & hours per day
		List<EmployeeAttendance> byReportsTo = this.findAllAttendancesByReportsTo(reportsTo);
          
		log.info("Under Repoting manager employee list :",byReportsTo);
		int sizeOfTeam = byReportsTo.size();

		int teamCount = 0;
		Duration teamTotalEffectiveDuration = Duration.ZERO;

		for (EmployeeAttendance teamAttendance : byReportsTo) {

			String teamEffectiveHoursStr = teamAttendance.getEffectiveHours();

			LocalTime effectiveHours = LocalTime.parse(teamEffectiveHoursStr, DateTimeFormatter.ofPattern("HH:mm:ss"));

			teamTotalEffectiveDuration = teamTotalEffectiveDuration.plusHours(effectiveHours.getHour())
					.plusMinutes(effectiveHours.getMinute());

			String status = teamAttendance.getStatus();

			if (status.equals("OnTime")) {

				teamCount++;
			}

		}

		long totalHoursTeam = teamTotalEffectiveDuration.toHours();
		long totalMinutesTeam = teamTotalEffectiveDuration.toMinutes() % 60;
		String totalEffectiveHoursTeam = String.format("%02d:%02d", totalHoursTeam, totalMinutesTeam);

		String teamAverageHoursPerDay = this.calculateAverageHoursPerDay(totalEffectiveHoursTeam, sizeOfTeam);

		double onTimeArivalTeam = ((double) teamCount / sizeOfTeam) * 100;
		int teamP = (int) onTimeArivalTeam;

		String onTimeArrivalPerOfTeam = String.valueOf(teamP) + " % ";

		log.info("Team On time avrial % : {}", onTimeArrivalPerOfTeam);
		log.info("Team Average Hours PerDay  :{}", teamAverageHoursPerDay);

		log.info("Employee on time arrival % : {}", onTimeArrivalPer);
		log.info("average hours per day :{}", averageHoursPerDay);
		avgDto.setTeamAvgPerDay(teamAverageHoursPerDay);
		avgDto.setAvgArivalPerTeam(onTimeArrivalPerOfTeam);
		avgDto.setAvgArivalPer(onTimeArrivalPer);
		avgDto.setAvgPerDay(averageHoursPerDay);

		aDto.add(avgDto);
		return aDto;

	}

	// Calculateing avg per week or month or custom range
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

	public List<EmployeeAttendance> findAllAttendancesByReportsTo(Long reportsTo) {
		Optional<List<Long>> employeeIdByReportsTo = employeeRepo.findEmployeeIdByReportsTo(reportsTo);

		return employeeIdByReportsTo.stream().flatMap(List::stream)
				.flatMap(empId -> attendanceRepository.findAllByEmployeeId(empId).stream())
				.collect(Collectors.toList());
	}

}
