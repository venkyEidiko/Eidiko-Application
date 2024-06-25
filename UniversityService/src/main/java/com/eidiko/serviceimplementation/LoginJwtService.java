package com.eidiko.serviceimplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import com.eidiko.dto.EmployeeDto;
import com.eidiko.dto.JwtTokenReturnClass;
import com.eidiko.entity.Employee;
import com.eidiko.entity.LoginEntity;
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.jwt.JwtService;
import com.eidiko.mapping.ModelMapperClass;
import com.eidiko.repository.EmployeeRepo;


import java.util.regex.Pattern;
@Service
//this class is loginservice class 
public class LoginJwtService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private ModelMapperClass modelMapperClass;
	

	 
	 //this method rutun's jwt token & also checks username(given username is email or phoneNumber)  
	    public JwtTokenReturnClass loginMethod1(LoginEntity loginEntity) throws Exception {
	        
	    	//it gets email 
	        String username = loginEntity.getEmail();
	        
	        // Authenticate the user
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                username,
	                loginEntity.getPassword()
	            )
	        );
	        
	        Employee employee;
	        
	        // Check if the username is an email or phoneNumber and fetch the user accordingly
	        if (isEmail1(username)) {
	            employee = employeeRepo.findByEmail(username)
	                .orElseThrow(() -> new BadRequestException("Employee Not Found With this Email: " + username));
	        } else if (isPhoneNumber1(username)) {
	            employee = employeeRepo.findByPhoneNu(username)
	                .orElseThrow(() -> new BadRequestException("Employee Not Found With this Phone Number: " + username));
	        } else {
	            throw new BadRequestException("Invalid username format: " + username);
	        }
	        
	        // Convert Employee object to EmployeeDto
	        EmployeeDto employeeDto = modelMapperClass.employeeToEmploteeDto(employee);
	        
	        // Generate JWT token
	        String jwtToken = jwtService.generateToken(loginEntity.getUsername());
	        //genarate Refresh token
	        String refreshToken=jwtService.generateRefreshToken(loginEntity.getUsername());
	        
	        // Return JwtTokenReturnClass with both jwtToken and employeeDto
	        return JwtTokenReturnClass
	        		.builder()
	        		.jwtToken(jwtToken)
	        		.employeeDto(employee)  // changed here employeedto to employee
	        		.refreshToken(refreshToken).build();
	    }
	    
	    // Check if the username is an email
	    private boolean isEmail1(String username) {
	        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
	        Pattern pattern = Pattern.compile(emailRegex);
	        return pattern.matcher(username).matches();
	    }

	    // Check if the username is a phone number
	    private boolean isPhoneNumber1(String username) {
	        String phoneRegex = "^[0-9]{10}$";
	        Pattern pattern = Pattern.compile(phoneRegex);
	        return pattern.matcher(username).matches();
	    }

	    
	    //this method is for refresh token genaration purpose
	    public String refreshTokenMethod(String token) {
	        
	    	String username = jwtService.extractEmailId(token);
	        System.out.println("username-----" + username);
	        
	        // Validate and find employee by email or phone number
	        
	        
	        // Validate the token
	        if (jwtService.validateToken1(token)) {
	            // Generate a new access token
	            String accessToken = jwtService.generateToken(username);
	            return accessToken;
	        } 
	        else {
	            // Throw TokenNotValidException if token is invalid
	            throw new BadRequestException("The provided token is not valid.");
	        }
	    
}}
