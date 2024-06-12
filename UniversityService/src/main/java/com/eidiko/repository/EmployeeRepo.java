package com.eidiko.repository;

import java.util.Optional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.Employee;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{
//<<<<<<< HEAD:UniversityService/src/main/java/com/eidiko/userrepository/EmployeeRepo.java
	Optional<Employee> findByEmail(String email);
	
//=======
	
	Optional<Employee> findByEmployeeId(int employeeId);
//>>>>>>> e23a7c1f3f12ad6d4b9c82c7c4c8a7abfc756b48:UniversityService/src/main/java/com/eidiko/repository/EmployeeRepo.java

}
