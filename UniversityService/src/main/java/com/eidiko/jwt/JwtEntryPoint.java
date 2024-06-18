package com.eidiko.jwt;



import java.io.PrintWriter;


import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws java.io.IOException, ServletException {
		// TODO Auto-generated method stub
		 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        PrintWriter writer = response.getWriter();
	        log.info("Spring enty point");
	        writer.println("Access Denied with error message : " + authException.getMessage());
		
	}

}
