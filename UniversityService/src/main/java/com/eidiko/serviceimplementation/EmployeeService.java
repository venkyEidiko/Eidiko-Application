package com.eidiko.serviceimplementation;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eidiko.dto.BirtdayAndanniversaryDto;
import com.eidiko.dto.EmployeeDto;
import com.eidiko.entity.Employee;
import com.eidiko.entity.Roles_Table;
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.mapper.Mapper;
import com.eidiko.repository.AddressRepo;
import com.eidiko.repository.EmployeeRepo;
import com.eidiko.repository.RolesReposotory;
import com.eidiko.service.EmployeeInterface;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService implements EmployeeInterface {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AddressRepo addressRepo;
	@Autowired
	private Mapper mapper;

	@Autowired
	private RolesReposotory rolesReposotory;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// save the employee details
	@Override
	public String saveEmployee(Employee employee) {

		Optional<Roles_Table> byRoleId = rolesReposotory.findByRoleId(employee.getRole().getRoleId());

//		Integer roleId = byRoleId.get().getRoleId();
//		if (employee.getRole().getRoleId() == roleId) {
//			Roles_Table save2 = rolesReposotory.save(employee.getRole());
//			employee.setRole(save2);
//
//		}

		Roles_Table save2 = rolesReposotory.save(employee.getRole());
		employee.setRole(save2);

		String encode = passwordEncoder.encode(employee.getPassword());
		log.info("Encode password :{}", encode);
		employee.setPassword(encode);

		this.validateUniqueEmail(employee.getEmail());

		this.validateUniquePhoneNumber(employee.getPhoneNu());
		Employee save = employeeRepo.save(employee);

		if (save != null && save.getEmployeeId() != 0) {
			return "your record has been created";
		} else {

			throw new BadRequestException("Failed to create User");
		}

	}

	private boolean validateUniqueEmail(String email) {
		log.info("validateUniqueEmail {}", email);
		Optional<Employee> byEmail = employeeRepo.findByEmail(email);

		if (byEmail.isPresent()) {
			throw new BadRequestException("This email already exists: " + email);
		} else {
			return true; // Email is unique
		}
	}

	private boolean validateUniquePhoneNumber(String phoneNu) {
		log.info("validateUniqueEmail {}", phoneNu);
		Optional<Employee> byEmail = employeeRepo.findByPhoneNu(phoneNu);

		if (byEmail.isPresent()) {
			throw new BadRequestException("This number already exists: " + phoneNu);
		} else {
			return true; // number is unique
		}
	}

	// for update the employee
	@Override
	public String updateEmployee(Long employeeId, Employee employee) throws UserNotFoundException {

		log.info("Employee id :{}", employeeId);
		Employee existingEmployee = employeeRepo.findByEmployeeId(employeeId)
				.orElseThrow(() -> new UserNotFoundException("User not found in database"));
		updateNonNullFields(existingEmployee, employee);
		Employee updatedEmployee = employeeRepo.save(existingEmployee);
		if (updatedEmployee.getEmployeeId() != 0) {
			return "User record has been updated";
		} else {
			return "Failed to save the updated employee record";
		}

	}

	// above update method used this method for if one field needs upadate then
	// remaining should not be null
	private void updateNonNullFields(Object target, Object source) {
		Field[] fields = source.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object value = field.get(source);
				if (value != null) {
					field.set(target, value);
				}
			} catch (IllegalAccessException e) {
				log.error("Failed to update field: {}", field.getName(), e);
			}
		}
	}

	public Employee getByEmail(String emial) throws UserNotFoundException {

		Employee emp = employeeRepo.findByEmail(emial).orElseThrow(() -> new UserNotFoundException("User not found"));

		return emp;

	}

	@Override
	public String updateEmployeeContactDetails(Long empLoyeeId, Employee employee) throws UserNotFoundException {
		Employee employee1 = employeeRepo.findById(empLoyeeId)
				.orElseThrow(() -> new UserNotFoundException(empLoyeeId + "is not available ! please signup !!"));
		employee1.setWorkEmail(employee.getWorkEmail());
		employee1.setPersonalEmail(employee.getPersonalEmail());
		employee1.setWorkNumber(employee.getWorkNumber());
		employee1.setResidenceNumber(employee.getResidenceNumber());
		employee1.setSkype(employee.getSkype());
		employee1.setEmergencyContactNumber(employee.getEmergencyContactNumber());
		Employee updatedEmployee = employeeRepo.save(employee1);

		if (updatedEmployee.getEmployeeId() != 0) {
			return "User record has been updated ! ";
		} else {
			throw new BadRequestException("User record has not been updated !");
		}

	}

	@Override
	public String updateEmployeePrimaryDetails(Long empLoyeeId, Employee employee) throws UserNotFoundException {
		Employee employee1 = employeeRepo.findById(empLoyeeId)
				.orElseThrow(() -> new UserNotFoundException(empLoyeeId + "is not available ! please signup !!"));
		employee1.setDateOfBirth(employee.getDateOfBirth());
		employee1.setMaritalStatus(employee.getMaritalStatus());
		employee1.setBloodGroup(employee.getBloodGroup());
		employee1.setPhysicallyHandicapped(employee.getPhysicallyHandicapped());
		employee1.setNationality(employee.getNationality());
		Employee updatedEmployee = employeeRepo.save(employee1);

		if (updatedEmployee.getEmployeeId() != 0) {
			return "User record has been updated ";
		} else {
			throw new BadRequestException("User record has not been updated !");
		}

	}

	@Override
	public String updateEmployeeJobDetails(Long empLoyeeId, Employee employee) throws UserNotFoundException {
		Employee employee1 = employeeRepo.findById(empLoyeeId)
				.orElseThrow(() -> new UserNotFoundException(empLoyeeId + "is not available ! please signup !!"));
		employee1.setDateOfJoining(employee.getDateOfJoining());
		employee1.setJobTitlePrimary(employee.getJobTitlePrimary());
		employee1.setJobTitleSecondry(employee.getJobTitleSecondry());
		employee1.setWorkerType(employee.getWorkerType());
		employee1.setTimeType(employee.getTimeType());
		employee1.setNoticePeriod(employee.getNoticePeriod());
		employee1.setPayBand(employee.getPayBand());
		employee1.setPayGrade(employee.getPayGrade());
		employee1.setContractStatus(employee.getContractStatus());
		employee1.setInProbation(employee.getInProbation());
		Employee updatedEmployee = employeeRepo.save(employee1);

		if (updatedEmployee.getEmployeeId() != 0) {
			return "User record has been updated ";
		} else {
			throw new BadRequestException("User record has not been updated !");
		}
	}

	@Override
	public String updateEmployeeTimeDetails(Long empLoyeeId, Employee employee) throws UserNotFoundException {
		Employee employee1 = employeeRepo.findById(empLoyeeId)
				.orElseThrow(() -> new UserNotFoundException(empLoyeeId + "is not available ! please signup !!"));
		employee1.setShift(employee.getShift());
		employee1.setWeeklyOffPolicy(employee.getWeeklyOffPolicy());
		employee1.setLeavePlan(employee.getLeavePlan());
		employee1.setHolidayCalendar(employee.getHolidayCalendar());
		employee1.setAttendanceCaptureScheme(employee.getAttendanceCaptureScheme());
		employee1.setAttendanceNumber(employee.getAttendanceNumber());
		employee1.setAttendancePenalisationPolicy(employee.getAttendancePenalisationPolicy());
		employee1.setShiftweeklyOffRule(employee.getShiftweeklyOffRule());
		employee1.setShiftAllowancePolicy(employee.getShiftAllowancePolicy());

		Employee updatedEmployee = employeeRepo.save(employee1);

		if (updatedEmployee.getEmployeeId() != 0) {
			return "User record has been updated ";
		} else {
			throw new BadRequestException("User record has not been updated !");
		}

	}

	@Override
	public String updateEmployeeOrganizationDetails(Long empLoyeeId, Employee employee) throws UserNotFoundException {
		Employee employee1 = employeeRepo.findById(empLoyeeId)
				.orElseThrow(() -> new UserNotFoundException(empLoyeeId + "is not available ! please signup !!"));
		employee1.setBusinessUnit(employee.getBusinessUnit());
		employee1.setDepartment(employee.getDepartment());
		employee1.setLocation(employee.getLocation());
		employee1.setCostCenter(employee.getCostCenter());
		employee1.setLegalEntity(employee.getLegalEntity());
		employee1.setDottedLineManager(employee.getDottedLineManager());
		employee1.setReportsTo(employee.getReportsTo());
		employee1.setManagerOfManager(employee.getManagerOfManager());
		employee1.setShiftAllowancePolicy(employee.getShiftAllowancePolicy());

		Employee updatedEmployee = employeeRepo.save(employee1);

		if (updatedEmployee.getEmployeeId() != 0) {
			return "User record has been updated ";
		} else {
			throw new BadRequestException("User record has not been updated !");
		}
	}

	// for birthdays and anniversaries
	@Override
	public Map<String, List<BirtdayAndanniversaryDto>> bithDayMethod(LocalDate date) {
		List<BirtdayAndanniversaryDto> employeesDataOfBirthList = employeeRepo.findBydateOfBirth(date.getMonthValue(),
				date.getDayOfMonth());
		List<BirtdayAndanniversaryDto> employeesDateOfJoiningList = employeeRepo
				.findByDateOfJoining(date.getMonthValue(), date.getDayOfMonth());

		// this loop is for count no of years employee completed
		for (BirtdayAndanniversaryDto dto : employeesDateOfJoiningList) {

			Employee employee = employeeRepo.findById(dto.getEmployeeId()).orElse(null);
			if (employee != null) {
				int yearsOfService = Period.between(employee.getDateOfJoining(), date).getYears();
				dto.setNoOfYearsCompletedInThisCompany(yearsOfService);
			}
		}

		Map<String, List<BirtdayAndanniversaryDto>> result = new HashMap<>();
		result.put("BirthDayToday", employeesDataOfBirthList);
		result.put("WorkAnniversaries", employeesDateOfJoiningList);

		return result;
	}

	// this will return both todays anniversary and next 7 days anniversary
	@Override
	public Map<String, List<Map<String, Object>>> getWorkAnniversariesForTodayAndNextSevenDays() {
		LocalDate today = LocalDate.now();
		LocalDate endDate = today.plusDays(7);

		List<Employee> allEmployees = employeeRepo.findAll();

		List<Map<String, Object>> todayAnniversaries = new ArrayList<>();
		List<Map<String, Object>> nextSevenDaysAnniversaries = new ArrayList<>();

		for (Employee employee : allEmployees) {
			LocalDate joiningDate = employee.getDateOfJoining();
			if (joiningDate != null) {
				LocalDate anniversaryThisYear = joiningDate.withYear(today.getYear());

				if (joiningDate.getYear() == today.getYear()) {
					continue;
				}

				int years = today.getYear() - joiningDate.getYear();

				Map<String, Object> anniversaryData = new HashMap<>();
				anniversaryData.put("employeeId", employee.getEmployeeId());
				anniversaryData.put("firstName", employee.getFirstName());
				anniversaryData.put("lastName", employee.getLastName());
				anniversaryData.put("years", years);

				if (anniversaryThisYear.equals(today)) {
					todayAnniversaries.add(anniversaryData);
				}

				if (anniversaryThisYear.isAfter(today) && anniversaryThisYear.isBefore(endDate)) {
					nextSevenDaysAnniversaries.add(anniversaryData);
				}
			}
		}

		Map<String, List<Map<String, Object>>> result = new HashMap<>();
		result.put("Today Anniversaries", todayAnniversaries);
		result.put("Next Seven Days Anniversaries", nextSevenDaysAnniversaries);

		return result;
	}

	@Override
	public Optional<List<Employee>> searchByKeywords(String keywords) {
		Optional<List<Employee>> employeeList = employeeRepo.searchByFirstNameOrLastNameOrEmployeeId(keywords);
		return employeeList;
	}

	
	// this method returns both present date and after 7 days birthdays
			@Override
			public Map<String, List<BirtdayAndanniversaryDto>> getBirthdaysAndAnniversariesForTodayAndNextSevenDays() {
				LocalDate today = LocalDate.now();
				LocalDate endDate = today.plusDays(8);
				log.info("next 7 days date {}", endDate);
				List<BirtdayAndanniversaryDto> todayBirthdays = employeeRepo.findBydateOfBirth(today.getMonthValue(),
						today.getDayOfMonth());

				List<BirtdayAndanniversaryDto> nextSevenDaysBirthdays = new ArrayList<>();
				List<Employee> allEmployees = employeeRepo.findAll();

				for (Employee employee : allEmployees) {
					LocalDate birthday = employee.getDateOfBirth();
					if (birthday != null) {
						// Set the birthday to the current year for comparison
						// (This means the month and day stay the same, but the year is changed to the
						// current year.)
						LocalDate birthdayThisYear = birthday.withYear(today.getYear());
						if (birthdayThisYear.isAfter(today) && birthdayThisYear.isBefore(endDate)) {

							BirtdayAndanniversaryDto dto = new BirtdayAndanniversaryDto(
									employee.getEmployeeId(),
									employee.getFirstName(), 
									employee.getLastName()
								

							);
							nextSevenDaysBirthdays.add(dto);
						}
					}
				}
				//for add dateof birth in the response
				for (BirtdayAndanniversaryDto dto : todayBirthdays) {

					Employee employee = employeeRepo.findById(dto.getEmployeeId()).orElse(null);
					if (employee != null) {
						LocalDate date  = employee.getDateOfBirth();
						dto.setDateOfBirth(date);
					}
				}

				//for add dateof birth in the response
						for (BirtdayAndanniversaryDto dto : nextSevenDaysBirthdays) {

							Employee employee = employeeRepo.findById(dto.getEmployeeId()).orElse(null);
							if (employee != null) {
								LocalDate date  = employee.getDateOfBirth();
								dto.setDateOfBirth(date);
							}
						}
				Map<String, List<BirtdayAndanniversaryDto>> result = new HashMap<>();
				result.put("TodayBirthdays", todayBirthdays);
				result.put("NextSevenDaysBirthdays", nextSevenDaysBirthdays);

				return result;
			}

		//this is for today and next seven days anniversary employee list along with count of they worked
		public Map<String, List<BirtdayAndanniversaryDto>> getTodayAndSevenDaysAnniversaryList() {
			LocalDate today = LocalDate.now();
			LocalDate endDate = today.plusDays(8);

			List<BirtdayAndanniversaryDto> todayAnniversary = employeeRepo.findByDateOfJoining(today.getMonthValue(),
					today.getDayOfMonth());
			List<BirtdayAndanniversaryDto> nextSevenDaysAnniversaries = new ArrayList<>();

			List<Employee> allEmployees = employeeRepo.findAll();

			for (Employee employee : allEmployees) {
				LocalDate dateOfjoining = employee.getDateOfJoining();

				if (dateOfjoining != null) {
					// Set the birthday to the current year for comparison
					// (This means the month and day stay the same, but the year is changed to the
					// current year.)
					LocalDate birthdayThisYear = dateOfjoining.withYear(today.getYear());
					if (birthdayThisYear.isAfter(today) && birthdayThisYear.isBefore(endDate)) {

						BirtdayAndanniversaryDto dto = new BirtdayAndanniversaryDto(
								employee.getEmployeeId(),
								employee.getFirstName(), 
								employee.getLastName()
							

						);
						nextSevenDaysAnniversaries.add(dto);
					}
				}
			}

			//the below counts next sevendays anniversary employees worked how many years
			for (BirtdayAndanniversaryDto dto : nextSevenDaysAnniversaries) {

				Employee employee = employeeRepo.findById(dto.getEmployeeId()).orElse(null);
				if (employee != null) {
					int yearsOfService = Period.between(employee.getDateOfJoining(), today).getYears();
					dto.setNoOfYearsCompletedInThisCompany(yearsOfService);
				}
			}

			//the below counts today anniversary employees worked how many years
			for (BirtdayAndanniversaryDto dto : todayAnniversary) {

				Employee employee = employeeRepo.findById(dto.getEmployeeId()).orElse(null);
				if (employee != null) {
					int yearsOfService = Period.between(employee.getDateOfJoining(), today).getYears();
					dto.setNoOfYearsCompletedInThisCompany(yearsOfService);
				}
			}

			Map<String, List<BirtdayAndanniversaryDto>> result = new HashMap<>();
			result.put("TodayAnniversary", todayAnniversary);
			result.put("NextSevenDaysAnniversary", nextSevenDaysAnniversaries);

			return result;
		}
	
	@Override
	public Employee getByEmployeeId(Long employeeId) {

		Employee byEmployeeId;
		try {
			byEmployeeId = employeeRepo.findByEmployeeId(employeeId)
					.orElseThrow(() -> new BadRequestException("Employee not found :" + employeeId));

			return byEmployeeId;

		} catch (BadRequestException e) {

			throw new BadRequestException("Employee not found ");

		}

	}

	public List<EmployeeDto> getAllEmployee(Long employeeId) throws UserNotFoundException, BadRequestException {
		// Find the employee by employeeId
		Employee byEmployeeId = employeeRepo.findByEmployeeId(employeeId)
				.orElseThrow(() -> new UserNotFoundException("User id not found"));

		// Get the reportsTo ID of the employee
		Long reportsTo = byEmployeeId.getReportsTo();

		// Validate that all employees have a valid reportsTo ID
		List<Employee> allEmployees = employeeRepo.findAll();
		for (Employee employee : allEmployees) {
			if (employee.getReportsTo() == null) {
				throw new BadRequestException("Required reports to id");
			}
		}

		// Filter employees based on reportsTo ID and convert to DTOs
		List<Employee> filteredEmployees = allEmployees.stream().filter(e -> e.getReportsTo().equals(reportsTo))
				.collect(Collectors.toList());

		// Check if filteredEmployees is empty
		if (filteredEmployees.isEmpty()) {
			throw new BadRequestException("No employees found for the given reportsTo id");
		}

		// Convert to DTOs
		List<EmployeeDto> employeeDtoList = new ArrayList<>();
		for (Employee employee : filteredEmployees) {
			EmployeeDto employeeDto = mapper.employeeToEmployeeDto(employee);
			employeeDtoList.add(employeeDto);
		}

		log.info("Employee DTOs: {}", employeeDtoList);
		return employeeDtoList;
	}

	// this will return both new joinees and last 7 days joinees
	@Override
	public Map<String, List<Map<String, Object>>> getNewJoinersForTodayAndLast7Days() {
		LocalDate today = LocalDate.now();
		LocalDate last7Days = today.minusDays(7);

		List<Employee> newJoinersTodayAndLast7Days = employeeRepo.findByDateOfJoiningBetween(last7Days, today);

		List<Map<String, Object>> newJoinersTodayList = new ArrayList<>();
		List<Map<String, Object>> newJoinersLast7DaysList = new ArrayList<>();

		for (Employee employee : newJoinersTodayAndLast7Days) {
			LocalDate joiningDate = employee.getDateOfJoining();
			if (joiningDate != null) {
				Map<String, Object> newJoinerData = new HashMap<>();
				newJoinerData.put("employeeId", employee.getEmployeeId());
				newJoinerData.put("firstName", employee.getFirstName());
				newJoinerData.put("lastName", employee.getLastName());
				newJoinerData.put("joinDate", joiningDate);

				if (joiningDate.equals(today)) {
					newJoinersTodayList.add(newJoinerData);
				} else if (joiningDate.isAfter(last7Days) && joiningDate.isBefore(today)) {
					newJoinersLast7DaysList.add(newJoinerData);
				}
			}
		}
		Map<String, List<Map<String, Object>>> response = new HashMap<>();
		response.put("newJoinersToday", newJoinersTodayList);
		response.put("newJoinersLast7Days", newJoinersLast7DaysList);

		return response;
	}

	@Override
	public List<BirtdayAndanniversaryDto> getEmployeesWithBirthdaysNextSevenDays() {
		// TODO Auto-generated method stub
		return null;
	}

}