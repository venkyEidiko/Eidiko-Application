package com.eidiko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.dto.JwtTokenReturnClass;
import com.eidiko.entity.LoginEntity;
import com.eidiko.serviceimplementation.LoginJwtService;


@CrossOrigin(origins="*")
@RestController
public class LoginJwtController {
	
	@Autowired
	private LoginJwtService loginJwtService;
	

	//this method is for login
	@PostMapping("/login1")
	public ResponseEntity<JwtTokenReturnClass> loginMethod1(@RequestBody LoginEntity loginEntity) throws Exception{
	
		JwtTokenReturnClass res = loginJwtService.loginMethod1(loginEntity);
		return ResponseEntity.ok(res);
		
	}
	
	//normal genaral end point handled method
	@GetMapping("/hello")
	public ResponseEntity<String> check(){
		 return ResponseEntity.ok("This is a public endpoint");
	}
	
	//this method is for to genarate refresh token
	@PostMapping("/refresh/{token}")
	public  ResponseEntity<String> refresh(@PathVariable String token){
		String accessToken=loginJwtService.refreshTokenMethod(token);
		
		return ResponseEntity.ok(accessToken);
		
		
	}

}
