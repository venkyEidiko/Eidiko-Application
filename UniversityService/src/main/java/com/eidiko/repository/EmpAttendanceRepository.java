package com.eidiko.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.EmployeeAttendance;

@Repository
public interface EmpAttendanceRepository extends CrudRepository<EmployeeAttendance, Long> {

	
	List<EmployeeAttendance>findByEmployeeId(Long employeeId);
	
	List<EmployeeAttendance> findByEmployeeIdAndDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
}
