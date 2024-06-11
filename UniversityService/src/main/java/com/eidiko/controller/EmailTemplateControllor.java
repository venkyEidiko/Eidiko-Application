package com.eidiko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

import com.eidiko.service.EmailTemplateInterface;

@RestController
public class EmailTemplateControllor {

	
	@Autowired
	private EmailTemplateInterface templateInterface;
	
	
	public ResponseEntity<String>template(){
		
		return null; 
	}
}
