package com.eidiko.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eidiko.entity.ResponseModel;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<ResponseModel<Object>> response(UserNotFound ex) {

		ResponseModel<Object> res = new ResponseModel<>();
		
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setStatus(ex.getMessage());

		return ResponseEntity.ok(res);

	}


	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ResponseModel<Object>> handlePasswordMismatchException(PasswordMismatchException ex){
		ResponseModel<Object> res=new ResponseModel<>();
	    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setStatus(ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}



}
