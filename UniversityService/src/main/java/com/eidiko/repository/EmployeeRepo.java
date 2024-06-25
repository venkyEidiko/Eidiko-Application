package com.eidiko.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eidiko.dto.BirtdayAndanniversaryDto;
import com.eidiko.entity.Employee;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{

	Optional<Employee> findByEmail(String email);

	Optional<Employee> findByEmployeeId(Long employeeId);
	
	Optional<Employee> findByPhoneNu(String username);



	
	Optional<Employee> findByFirstName(String firstName);

	Optional<Employee> findByLastName(String lastName);

	
	//for birthdays and anniversaries giving 
	@Query("SELECT new com.eidiko.dto.BirtdayAndanniversaryDto(e.employeeId, e.firstName, e.lastName) " +
	           "FROM Employee e WHERE MONTH(e.dateOfBirth) = :month AND DAY(e.dateOfBirth) = :day")
	    List<BirtdayAndanniversaryDto> findBydateOfBirth(@Param("month") int monthValue, @Param("day") int dayOfMonth);

	    @Query("SELECT new com.eidiko.dto.BirtdayAndanniversaryDto(e.employeeId, e.firstName, e.lastName) " +
	           "FROM Employee e WHERE MONTH(e.dateOfJoining) = :month AND DAY(e.dateOfJoining) = :day")
	    List<BirtdayAndanniversaryDto> findByDateOfJoining(@Param("month") int monthValue, @Param("day") int dayOfMonth);
}
