package com.eidiko.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eidiko.dto.EmployeeDto;
import com.eidiko.dto.JwtTokenReturnClass;
import com.eidiko.entity.Employee;
import com.eidiko.entity.LoginEntity;
import com.eidiko.exception_handler.UserNotFound;
import com.eidiko.jwt.JwtService;
import com.eidiko.mapping.ModelMapperClass;
import com.eidiko.repository.EmployeeRepo;

@Service
public class LoginJwtService {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private ModelMapperClass modelMapperClass;
	

	 
	 public JwtTokenReturnClass loginMethod(LoginEntity loginEntity)throws Exception {

		 	//this calls tokenGenarateMethod method that returns token then it stores in string
	        String jwtToken = jwtService.tokenGenarateMethod(loginEntity);

	        // Fetch Employee details using email from LoginEntity
	        Employee employee = employeeRepo.findByEmail(loginEntity.getEmail())
	                .orElseThrow(() -> new UserNotFound("Employee Not Found With this Email: "+loginEntity.getEmail()));
	        
	        //it checks the database password and user entered password are correct are not
	        //if its not correct then it will exception handled class
	        if (!loginEntity.getPassword().equals(employee.getPassword())) {
	            throw new UserNotFound("Invalid password or username" );
	        }
	        
	        //this is for convert Employee obj to EmployeeDto object
	        EmployeeDto employeeDto = modelMapperClass.employeeToEmploteeDto(employee);

	        // Return JwtTokenReturnClass with both jwtToken and employee
	        return JwtTokenReturnClass.builder().jwtToken(jwtToken).employeeDto(employeeDto).build();
	    }
	

}
