package com.eidiko.serviceimplementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.hssf.record.PageBreakRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eidiko.dto.BirtdayAndanniversaryDto;
import com.eidiko.entity.Address;
import com.eidiko.entity.Employee;
import com.eidiko.entity.Roles_Table;
//import com.eidiko.entity.Roles;
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
	@Override
	public String updateEmployee(Long employeeId, Employee employee) throws UserNotFoundException {

		log.info("Employee id :{}", employeeId);

		Employee byEmployeeId = employeeRepo.findByEmployeeId(employeeId)
				.orElseThrow(() -> new UserNotFoundException("User not found in database"));

		byEmployeeId.setEmail(employee.getEmail());
		byEmployeeId.setFirstName(employee.getFirstName());
		byEmployeeId.setGender(employee.getGender());
		byEmployeeId.setLastName(employee.getLastName());
		// byEmployeeId.setRole(employee.getRole());

		// Update addresses
		List<Address> addresses = employee.getAddresses();
		if (addresses != null) {
			// Update existing addresses or add new addresses
			List<Address> updatedAddress = new ArrayList<>();

			// address.setEmployee(byEmployeeId);

			for (Address address1 : byEmployeeId.getAddresses()) {
				for (Address address : addresses) {
					if (address1.getAddressType().equalsIgnoreCase(address.getAddressType())) {

						address1.setAddressType(address.getAddressType());
						address1.setArea(address.getArea());
						address1.setCity(address.getCity());
						address1.setDoorNumber(address.getDoorNumber());
						address1.setLandmark(address.getLandmark());
						address1.setPincode(address.getPincode());
						address1.setState(address.getState());
						address1.setStreetName(address.getStreetName());
						updatedAddress.add(address1);
					} else {
						address.setEmployee(byEmployeeId);
						updatedAddress.add(address);
					}
				}
				byEmployeeId.setAddresses(updatedAddress);
			}
		}
		Employee updatedEmployee = employeeRepo.save(byEmployeeId);

		if (updatedEmployee.getEmployeeId() != 0) {
			return "User record has been updated ";
		} else {
			return "Failed to save the updated employee record";
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



	@Override
	public Optional<List<Employee>> searchByKeywords(String keywords) {
		Optional<List<Employee>> employeeList = employeeRepo.searchByFirstNameOrLastNameOrEmployeeId(keywords);
		return employeeList;
	}

//for birthdays and anniversaries giving 
@Override
public Map<String, List<BirtdayAndanniversaryDto>> bithDayMethod(LocalDate date) {	
	List<BirtdayAndanniversaryDto>employeesDataOfBirthList= employeeRepo.findBydateOfBirth(date.getMonthValue(),date.getDayOfMonth());
	 List<BirtdayAndanniversaryDto> employeesDateOfJoiningList = employeeRepo.findByDateOfJoining(date.getMonthValue(), date.getDayOfMonth());
	
	 Map<String, List<BirtdayAndanniversaryDto>> result = new HashMap<>();
	  result.put("BirthDay Today ", employeesDataOfBirthList);
      result.put("Work Anniversaries ", employeesDateOfJoiningList);
      
	 return result;

}


   //this is for getting the employeedetails birthday date from todays date to next seven days
	@Override
	public List<BirtdayAndanniversaryDto> getEmployeesWithBirthdaysNextSevenDays() {
		LocalDate today = LocalDate.now();
		LocalDate endDate = today.plusDays(7);

		List<Employee> allEmployees = employeeRepo.findAll();
		List<BirtdayAndanniversaryDto> employeesWithBirthdaysNextSevenDays = new ArrayList<>();

		for (Employee employee : allEmployees) {
			LocalDate birthday = employee.getDateOfBirth();
			if (birthday != null && (birthday.isEqual(today) || (birthday.isAfter(today) && birthday.isBefore(endDate)))) {
				BirtdayAndanniversaryDto dto = new BirtdayAndanniversaryDto(
						employee.getEmployeeId(),
						employee.getFirstName(),
						employee.getLastName()
				);
				employeesWithBirthdaysNextSevenDays.add(dto);
			}
		}

		return employeesWithBirthdaysNextSevenDays;
	}






	//this method returns both present date and after 7 days birthdays
	@Override
	public Map<String, List<BirtdayAndanniversaryDto>> getBirthdaysAndAnniversariesForTodayAndNextSevenDays() {
		LocalDate today = LocalDate.now();
		LocalDate endDate = today.plusDays(7);

		List<BirtdayAndanniversaryDto> todayBirthdays = employeeRepo.findBydateOfBirth(today.getMonthValue(), today.getDayOfMonth());

		List<BirtdayAndanniversaryDto> nextSevenDaysBirthdays = new ArrayList<>();
		List<Employee> allEmployees = employeeRepo.findAll();

		for (Employee employee : allEmployees) {
			LocalDate birthday = employee.getDateOfBirth();
			if (birthday != null &&  (birthday.isAfter(today) && birthday.isBefore(endDate))) {
				BirtdayAndanniversaryDto dto = new BirtdayAndanniversaryDto(
						employee.getEmployeeId(),
						employee.getFirstName(),
						employee.getLastName()
				);
				nextSevenDaysBirthdays.add(dto);
			}
		}

		Map<String, List<BirtdayAndanniversaryDto>> result = new HashMap<>();
		result.put("Today Birthdays", todayBirthdays);
		result.put("Next Seven Days Birthdays", nextSevenDaysBirthdays);

		return result;
	}




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



	//this will return both new joinees and last 7 days joinees
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
		response.put("new Joiners Today", newJoinersTodayList);
		response.put("new Joiners Last 7 Days", newJoinersLast7DaysList);

		return response;
	}





}