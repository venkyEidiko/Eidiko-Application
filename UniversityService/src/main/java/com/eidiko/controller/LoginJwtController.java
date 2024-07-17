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
import com.eidiko.entity.ResponseModel;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.LoginJwtService;


@CrossOrigin(origins="*")
@RestController
public class LoginJwtController {
	
	@Autowired
	private LoginJwtService loginJwtService;
	@Autowired
	private   CommonResponse<JwtTokenReturnClass> commonResponse;
	@Autowired
	private   CommonResponse<String> commonResponse1;
	
	//this method is for login
	@PostMapping("/login1")
    public ResponseEntity<ResponseModel<JwtTokenReturnClass>> loginMethod1(@RequestBody LoginEntity loginEntity) {
      
		try {
            JwtTokenReturnClass res = loginJwtService.loginMethod1(loginEntity);
            return commonResponse.prepareSuccessResponseObject(res);
        } catch (Exception e) {
            return commonResponse.prepareFailedResponse(e.getMessage());
        }
    }
	
	//normal genaral end point handled method
	@GetMapping("/hello")
	public ResponseEntity<String> check(){
		System.out.println("--------hello ");
		 return ResponseEntity.ok("This is a public endpoint");
	}
	

	@GetMapping("/refresh/{token}")
    public ResponseEntity<ResponseModel<String>> refresh(@PathVariable String token) {
        try {
            String accessToken = loginJwtService.refreshTokenMethod(token);
            return commonResponse1.prepareSuccessResponseObject(accessToken);
        } catch (Exception e) {
            return commonResponse1.prepareFailedResponse(e.getMessage());
        }
    }
}
