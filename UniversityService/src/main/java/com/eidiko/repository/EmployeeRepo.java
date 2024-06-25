package com.eidiko.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.Employee;
import com.eidiko.exception_handler.UserNotFoundException;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{

	Optional<Employee> findByEmail(String email);

	Optional<Employee> findByEmployeeId(Long employeeId);
	
	Optional<Employee> findByPhoneNu(String username);

	@Query("select e from Employee e where lower(e.firstName) like lower(concat('%', :key, '%')) " +
	           "or lower(e.lastName) like lower(concat('%', :key, '%')) " +
	           "or cast(e.employeeId as string) like concat('%', :key, '%')")
	    Optional<List<Employee>> searchByFirstNameOrLastNameOrEmployeeId(@Param("key") String keyword);

	 @Query("SELECT e.employeeId FROM Employee e WHERE e.reportsTo = :reportsTo")
	    Optional<List<Long>> findEmployeeIdByReportsTo(@Param("reportsTo") Long reportsTo);

//	Optional<Employee> findByPhoneNu(String phoneNu);

	Optional<Employee> findByFirstName(String firstName);

	Optional<Employee> findByLastName(String lastName);


}
