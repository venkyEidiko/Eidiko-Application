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

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ResponseModel<Object>> response(BadRequestException ex) {

		ResponseModel<Object> res = new ResponseModel<>();

		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setStatus(ex.getMessage());
		res.setError(ex.getMessage());
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ResponseModel<Object>> handlePasswordMismatchException(PasswordMismatchException ex) {
		return new CommonResponse<>().prepareErrorResponseObject(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	 @ExceptionHandler(SaveFailureException.class)
	    public ResponseEntity<com.eidiko.entity.Error> handleSaveFailureException(SaveFailureException ex) {
//	        Error error = new Error();
		 com.eidiko.entity.Error error = new com.eidiko.entity.Error();
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
//	 @ExceptionHandler(UserNotFoundException.class)
//	    public ResponseEntity<ResponseModel<Object>> handleUserNotFoundException(UserNotFoundException ex) {
//	        ResponseModel<Object> response = new ResponseModel<>();
//	        
//	        response.setEmail(null);
//	        response.setError(ex.getMessage());
//	        response.setResult(null);
//	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
//	        response.setStatus("FAILED");
//	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//	    }

}
