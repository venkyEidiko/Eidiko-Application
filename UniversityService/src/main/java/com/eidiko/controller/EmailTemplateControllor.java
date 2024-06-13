package com.eidiko.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.entity.EmailTemplate;
import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.service.EmailTemplateInterface;
import com.eidiko.serviceimplementation.EmailTemplateImp;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmailTemplateControllor {

	@Autowired
	private EmailTemplateInterface templateInterface;

	@Autowired
	private EmailTemplateImp emailTemplateImp;

	@GetMapping("/template/{name}")
	public ResponseEntity<Object> getTemplate(@PathVariable String name) throws UserNotFoundException {
		EmailTemplate template = templateInterface.getByTemplateName(name);

		if (template == null) {
			// Template not found
			return new ResponseEntity<>(template, HttpStatus.NOT_FOUND);
		}

		// Template found, return it with a 200 OK status
		return new ResponseEntity<>(template, HttpStatus.OK);
	}

	@PostMapping("/sendMail")
	public ResponseEntity<ResponseModel<Object>> sendMail(@RequestParam("toMail") String to,
			@RequestParam("otp") String otp) {
		try {
			
			String sendEmailWithOtp = emailTemplateImp.sendEmailWithOtp(to, otp);

			System.out.println("sendMail controller: " + sendEmailWithOtp);
			return new CommonResponse<>().prepareSuccessResponseObject(sendEmailWithOtp);
		} catch (UserNotFoundException e) {
			return new CommonResponse<>().prepareFailedResponse2( e.getMessage());
		} catch (IllegalArgumentException e) {
			return new CommonResponse<>().prepareFailedResponse1("Invalid input: " + e.getMessage());
		} catch (MessagingException | IOException e) {
			return new CommonResponse<>().prepareFailedResponse1("Internal Server Error: " + e.getMessage());
		} catch (RuntimeException e) {
			return new CommonResponse<>().prepareFailedResponse1("Unexpected Error: " + e.getMessage());
		}
	}

}
