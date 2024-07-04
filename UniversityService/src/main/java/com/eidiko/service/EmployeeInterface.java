package com.eidiko.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.eidiko.dto.BirtdayAndanniversaryDto;

import java.util.List;
import java.util.Optional;


import com.eidiko.entity.Address;
import com.eidiko.entity.Employee;
import com.eidiko.exception_handler.UserNotFoundException;

public interface EmployeeInterface {
	
	public String saveEmployee(Employee employee);
	
	public  Optional<List<Employee>> searchByKeywords(String keywords) ;
	
	public String updateEmployee(Long empID,Employee employee) throws UserNotFoundException;
	
	public String updateEmployeeContactDetails(Long empLoyeeId,Employee employee)throws UserNotFoundException;
	
	public String updateEmployeePrimaryDetails(Long empLoyeeId,Employee employee)throws UserNotFoundException;
	
	public String updateEmployeeJobDetails(Long empLoyeeId,Employee employee)throws UserNotFoundException;
	
	public String updateEmployeeTimeDetails(Long empLoyeeId,Employee employee)throws UserNotFoundException;
	
	public String updateEmployeeOrganizationDetails(Long empLoyeeId,Employee employee)throws UserNotFoundException;
	
	Map<String, List<BirtdayAndanniversaryDto>> bithDayMethod(LocalDate date);

	Map<String, List<Map<String, Object>>> getNewJoinersForTodayAndLast7Days();

	Map<String, List<BirtdayAndanniversaryDto>> getBirthdaysAndAnniversariesForTodayAndNextSevenDays();

    public Employee getByEmployeeId(Long employeeId)throws UserNotFoundException;

	List<BirtdayAndanniversaryDto> getEmployeesWithBirthdaysNextSevenDays();

	Map<String, List<Map<String, Object>>> getWorkAnniversariesForTodayAndNextSevenDays();



} 