package com.eidiko.responce;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.FileExtensionNotFound;
import com.eidiko.exception_handler.FormatMismatchException;
import com.eidiko.exception_handler.SaveFailureException;
import com.eidiko.exception_handler.UserNotFoundException;

@ControllerAdvice
@Component
public class CommonResponse<T> {

	public ResponseEntity<ResponseModel<T>> prepareSuccessResponseObject(T result) {
		ResponseModel<T> response = new ResponseModel<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setStatus("SUCCESS");
		if (result instanceof List<?>) {
			response.setResult((List<T>) result);
		} else {
			response.setResult(List.of(result));
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


// this is commonresponse
	public ResponseEntity<ResponseModel<T>> prepareSuccessResponseObject(T result, T password) {
		ResponseModel<T> response = new ResponseModel<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setStatus("SUCCESS");
		response.setPassword(password);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<ResponseModel<T>> prepareErrorResponseObject(String message, HttpStatus status) {
		ResponseModel<T> response = new ResponseModel<>();
		response.setStatus("FAILED");
		response.setStatusCode(status.value());
		response.setError(message);
		response.setResult(List.of((T) message));
		return new ResponseEntity<>(response, status);
	}

	public ResponseEntity<ResponseModel<T>> prepareFailedResponse(String error) {
		ResponseModel<T> response = new ResponseModel<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setStatus("FAILURE");
		response.setError(error);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<ResponseModel<T>> prepareSuccessResponseEmail(T result) {
		ResponseModel<T> response = new ResponseModel<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setStatus("SUCCESS");

		if (result instanceof List<?>) {
			response.setResult((List<T>) result);
		} else {
			response.setResult(List.of(result));
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseModel<T>> prepareFailedResponse1(T error) {
		ResponseModel<T> response = new ResponseModel<>();
		response.setStatus("FAILED");
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setResult(null);
		response.setError(error.toString());
		response.setEmail(null);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<ResponseModel<T>> prepareFailedResponse2(String error) {
		ResponseModel<T> response = new ResponseModel<>();
		response.setStatus("FAILED");
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setResult(null);
		response.setError(error);
		response.setEmail(null);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<ResponseModel<T>> prepareSuccessResponseObject(List<T> result) {
		ResponseModel<T> response = new ResponseModel<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setStatus("SUCCESS");
		response.setResult(result);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	// Globalexceptionhadler

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseModel<T>> handleUserNotFoundException(UserNotFoundException ex) {
		ResponseModel<T> response = new ResponseModel<>();

		response.setEmail(null);
		response.setError(ex.getMessage());
		response.setResult(null);
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setStatus("FAILED");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ResponseModel<T>> handleBadRequestException(T ex) {
		ResponseModel<T> response = new ResponseModel<>();

		response.setEmail(null);
		response.setError(ex.toString());
		response.setResult(null);
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setStatus("FAILED");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SaveFailureException.class)
	public ResponseEntity<ResponseModel<T>> handleSaveFailureException(SaveFailureException ex) {
		ResponseModel<T> response = new ResponseModel<>();

		response.setError(ex.getMessage());
		response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setResult(null);

		response.setStatus("FAILED");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FileExtensionNotFound.class)
	public ResponseEntity<ResponseModel<T>> handleFileExtensionNotFound(FileExtensionNotFound ex) {
		ResponseModel<T> response = new ResponseModel<>();

		response.setEmail(null);
		response.setError(ex.getMessage());
		response.setResult(null);
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setStatus("FAILED");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FormatMismatchException.class)
	public ResponseEntity<ResponseModel<T>> handleFormatMismatchException(String ex) {
		ResponseModel<T> response = new ResponseModel<>();

		response.setEmail(null);
		response.setError(ex);
		response.setResult(null);
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setStatus("FAILED");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
