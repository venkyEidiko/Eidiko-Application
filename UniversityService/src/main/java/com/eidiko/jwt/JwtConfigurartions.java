package com.eidiko.jwt;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class JwtConfigurartions {
	
	@Bean
	public SecurityFilterChain config(HttpSecurity httpSecurity)throws Exception {
		
		return httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req->
				req.requestMatchers("/login")
				.permitAll()
				.anyRequest()
				.permitAll())
				.build();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
