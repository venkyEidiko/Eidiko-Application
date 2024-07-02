package com.eidiko.serviceimplementation;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eidiko.dto.BirtdayAndanniversaryDto;
import com.eidiko.entity.Employee;
import com.eidiko.entity.Roles_Table;
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.exception_handler.UserNotFoundException;
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
	private RolesReposotory rolesReposotory;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// save the employee details
	@Override
	public String saveEmployee(Employee employee) {

		Optional<Roles_Table> byRoleId = rolesReposotory.findByRoleId(employee.getRole().getRoleId());

		Integer roleId = byRoleId.get().getRoleId();
		if (employee.getRole().getRoleId() == roleId) {
			Roles_Table save2 = rolesReposotory.save(employee.getRole());
			employee.setRole(save2);

		}
		String encode = passwordEncoder.encode(employee.getPassword());
		log.info("Encode password :{}", encode);
		employee.setPassword(encode);
		Employee save = employeeRepo.save(employee);

		if (save != null && save.getEmployeeId() != 0) {
			return "200";
		} else {

			throw new BadRequestException("Failed to create User");
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



	@Override
	public List<BirtdayAndanniversaryDto> getEmployeesWithBirthdaysNextSevenDays() {
		// TODO Auto-generated method stub
		return null;
	}


}