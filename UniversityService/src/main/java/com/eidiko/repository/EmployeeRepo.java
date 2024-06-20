package com.eidiko.repository;

import java.util.Optional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.Employee;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

	Optional<Employee> findByEmail(String email);
	
	

	

	Optional<Employee> findByEmployeeId(int employeeId);



	Optional<Employee> findByPhoneNu(String username);

}
