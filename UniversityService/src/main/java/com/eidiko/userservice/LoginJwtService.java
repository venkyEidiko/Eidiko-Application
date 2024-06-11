package com.eidiko.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eidiko.dto.EmployeeDto;
import com.eidiko.dto.JwtTokenReturnClass;
import com.eidiko.entity.Employee;
import com.eidiko.entity.LoginEntity;
import com.eidiko.jwt.JwtService;
import com.eidiko.mapping.ModelMapperClass;
import com.eidiko.userrepository.EmployeeRepo;

@Service
public class LoginJwtService {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private ModelMapperClass modelMapperClass;
	
//	public JwtTokenReturnClass loginMethod(LoginEntity loginEntity) {
//		
//		String jwtToken = jwtService.tokenGenarateMethod(loginEntity);
//		
//		return JwtTokenReturnClass.builder().jwtToken(jwtToken).build();
//	}
	 
	 public JwtTokenReturnClass loginMethod(LoginEntity loginEntity) {

	        String jwtToken = jwtService.tokenGenarateMethod(loginEntity);

	        // Fetch Employee details using email from LoginEntity
	        Employee employee = employeeRepo.findByEmail(loginEntity.getEmail())
	                .orElseThrow(() -> new RuntimeException("Employee not found"));
	        
	        EmployeeDto employeeDto = modelMapperClass.employeeToEmploteeDto(employee);

	        // Return JwtTokenReturnClass with both jwtToken and employee
	        return JwtTokenReturnClass.builder().jwtToken(jwtToken).employeeDto(employeeDto).build();
	    }
	

}
