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
		
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setStatus(ex.getMessage());
		res.setError(ex.getMessage());
		return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);

	}

}
