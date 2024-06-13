package com.eidiko.exception_handler;

import com.eidiko.responce.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eidiko.entity.ResponseModel;

@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private CommonResponse commonResponse;

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<ResponseModel<Object>> response(UserNotFound ex) {

		ResponseModel<Object> res = new ResponseModel<>();
		
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setStatus(ex.getMessage());

		return ResponseEntity.ok(res);

	}

	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ResponseModel<?>> handlePasswordMismatchException(PasswordMismatchException ex){
		return commonResponse.prepareErrorResponseObject(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
