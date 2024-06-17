package com.eidiko.exception_handler;

import com.eidiko.responce.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eidiko.entity.ResponseModel;

//<<<<<<< HEAD
//@RestControllerAdvice
//=======
import com.eidiko.entity.Error;
@ControllerAdvice

public class GlobalExceptionHandler {

	@Autowired
	private CommonResponse commonResponse;

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<ResponseModel<Object>> response(UserNotFound ex) {

		ResponseModel<Object> res = new ResponseModel<>();
		
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setStatus(ex.getMessage());
		res.setError(ex.getMessage());
		return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ResponseModel<?>> handlePasswordMismatchException(PasswordMismatchException ex){
		return commonResponse.prepareErrorResponseObject(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<com.eidiko.entity.Error> handleUserNotFoundException(UserNotFoundException ex) {
        Error error = new Error();
        error.setError(ex.getMessage());
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
	
	 @ExceptionHandler(SaveFailureException.class)
	    public ResponseEntity<com.eidiko.entity.Error> handleSaveFailureException(SaveFailureException ex) {
	        Error error = new Error();
	        error.setError(ex.getMessage());
	        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	    }


	@ExceptionHandler(FileUploadException.class)
	public ResponseEntity<ResponseModel<Object>> handleFileUploadException(FileUploadException ex) {
		return commonResponse.prepareErrorResponseObject(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidFileException.class)
	public ResponseEntity<ResponseModel<Object>> handleInvalidFileException(InvalidFileException ex) {
		return commonResponse.prepareErrorResponseObject(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
