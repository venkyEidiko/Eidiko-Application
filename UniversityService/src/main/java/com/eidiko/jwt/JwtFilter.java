package com.eidiko.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eidiko.exception_handler.BadRequestException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter  extends OncePerRequestFilter{
	
	
	public final  JwtService jwtService;

	public final  UserDetailsService customUserDetailsService;

	//constructor injection for UserDetailsService,JwtService
	public JwtFilter(JwtService jwtService, UserDetailsService customUserDetailsService) {
		super();
		this.jwtService = jwtService;
		this.customUserDetailsService = customUserDetailsService;
	}

	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("filter class entered...!");
		try {

			String header = request.getHeader("Authorization");
			System.out.println("header in filter class :" + header);
			//checks header(token) is null and stats with Bearer
			if (header == null || !header.startsWith("Bearer ")) {
				System.out.println("--------");
				filterChain.doFilter(request, response); // Proceed with the filter chain
				System.out.println("after filter.....!");
				return;
			}
			
			//take token after 7 letters 
			String token = header.substring(7);
			System.out.println("token:-" + token);
			
			//extract mail from token
			String emailId = this.jwtService.extractEmailId(token);
			System.out.println("emailId :" + emailId);
			
			//check email null then raise exception
			if (emailId == null) {
				throw new BadRequestException("User not found" + emailId);
			}
			System.out.println("User name after if :" + emailId);

			UserDetails userDetails = customUserDetailsService.loadUserByUsername(emailId);
            System.out.println(userDetails.getPassword());
            log.info("load by userName form filter :{}",userDetails.getUsername());
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			 filterChain.doFilter(request, response);

		} catch (Exception e) {
			log.error("Error occurred in JwtAuthenticationFilter", e);
			//throw new ServletException(e+"---------------------------------------");
			throw new BadRequestException("sorry");
		}

	}


}
