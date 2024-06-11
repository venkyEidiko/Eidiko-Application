package com.eidiko.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eidiko.entity.*;
import java.util.*;
import com.eidiko.userrepository.EmployeeRepo;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	
	public List<Employee> getMethod(){
		
		List<Employee> empList = employeeRepo.findAll();
		return empList;
		
	}
	
	public Employee saveMethod(Employee emp) {
		Employee emp1 = employeeRepo.save(emp);
		return emp1;
	}
	
	public Employee getByMailAdress(String mail)  {
		Employee emp1 = employeeRepo.findByEmail(mail).get();
		return emp1;
	}
	

}
