package com.eidiko.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

import com.eidiko.service.EmailTemplateInterface;
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

	public ResponseEntity<String> template() {

		return null;
	}

	@Autowired
	private EmailTemplateImp emailTemplateImp;

	// this method is for the template verification
	@GetMapping("/template/{name}")
	public ResponseEntity<ResponseModel<Object>> getTemplate(@PathVariable("name") String name) {

		try {
			EmailTemplate template = templateInterface.getByTemplateName(name);

			return new CommonResponse<>().prepareSuccessResponseObject(template);
		} catch (UserNotFoundException message) {
			return new CommonResponse<>().handleUserNotFoundException(message);
		}

	}

	// This is for sending mail to registered user mail
	@GetMapping("/sendMail")
	public ResponseEntity<ResponseModel<Object>> sendMail(@RequestParam("toMail") String to) {

		
		System.out.println(" send mail "+ to);
		try {
			String sendEmailWithOtp = emailTemplateImp.sendEmailWithOtp(to);
			return new CommonResponse<>().prepareSuccessResponseObject(sendEmailWithOtp);
		} catch (UserNotFoundException message) {
			return new CommonResponse<>().handleUserNotFoundException(message);
		} catch (IllegalArgumentException message) {
			return new CommonResponse<>().prepareFailedResponse1("Invalid input: " + message);
		} catch (MessagingException | IOException message) {
			return new CommonResponse<>().prepareFailedResponse1("Internal Server Error: " + message);
		} catch (RuntimeException message) {
			return new CommonResponse<>().prepareFailedResponse1("Unexpected Error: " + message);
		}
	}
}
