package com.eidiko.jwt;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.repository.EmployeeRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity

@RequiredArgsConstructor
//this class consist all configurations beans required for security
public class JwtConfigurartions {

	@Autowired
	public JwtService jwtService;
	@Autowired
	private JwtEntryPoint jwtEntryPoint;

	@Bean
	public JwtFilter jwtAuthenticationFilter()  {

		return new JwtFilter(jwtService, userDetailsService());

	}

	@Autowired
	private EmployeeRepo employeeRepo;

	// for encoding the password
	@Bean
	public PasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}

	// for fetch user details from db and also checks username is phonNum or mail
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> {
			if (isEmail(username)) {
				return employeeRepo.findByEmail(username)
						.orElseThrow(() -> new BadRequestException("User Not Found With This Email: " + username));
			} else if (isPhoneNumber(username)) {
				return employeeRepo.findByPhoneNu(username).orElseThrow(
						() -> new BadRequestException("User Not Found With This Phone Number: " + username));
			} else {
				throw new BadRequestException("Invalid user : " + username);
			}
		};
	}


	  
	// Helper methods to check if the username is an email or phone number
	private boolean isEmail(String username) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
		return username.matches(emailRegex);
	}

	private boolean isPhoneNumber(String username) {
		String phoneRegex = "^[0-9]{10}$";
		return username.matches(phoneRegex);
	}

	// this is Authentication Provider,we are used DaoAuthentication
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(pwdEncoder());
		return daoAuthenticationProvider;
	}

	// this is AuthenticationManager
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
		log.info("http security");
		return httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req -> 
				req.requestMatchers("/login1", "/refresh/**","/api/save","/api/password/forgotPassword","/api/getByEmail/**","/api/sendMail").permitAll()
						.anyRequest().authenticated())

		    	.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtEntryPoint))
				// .sessionManagement(session ->
				// session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).build();
	}

	// this bean is for ModelMapper (convert Employee obj to EmployeeDto we used
	// this)
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
