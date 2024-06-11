package com.eidiko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.dto.JwtTokenReturnClass;
import com.eidiko.entity.LoginEntity;
import com.eidiko.serviceimplementation.LoginJwtService;

@RestController
public class LoginJwtController {
	
	@Autowired
	private LoginJwtService loginJwtService;
	
//	@PostMapping("/login")
//	public ResponseEntity<JwtTokenReturnClass> loginMethod(@RequestBody LoginEntity loginEntity){
//		
//		JwtTokenReturnClass token = loginJwtService.loginMethod(loginEntity);
//		
//		return ResponseEntity.ok(token);
//		
//	}
	
	@PostMapping("/login")
    public ResponseEntity<JwtTokenReturnClass> loginMethod(@RequestBody LoginEntity loginEntity) throws Exception {

        JwtTokenReturnClass tokenResponse = loginJwtService.loginMethod(loginEntity);

        return ResponseEntity.ok(tokenResponse);
    }
	
	
	
	

}
