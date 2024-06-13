package com.eidiko.responce;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eidiko.entity.ResponseModel;

public class CommonResponse<T> {

	ResponseModel<T> response = new ResponseModel<>();
	public ResponseEntity<ResponseModel<T>> prepareSuccessResponseObject(T result) {
	
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setStatus("SUCCESS");

		if (result instanceof List<?>) {
			response.setResult((List<T>) result);
		} else {
			response.setResult(List.of(result));
		}

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseModel<T>> prepareFailedResponse(String error) {
		
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setStatus(error);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseModel<T>> prepareSuccessResponseEmail(T result) {
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setStatus("SUCCESS");

		response.setEmail((String) result);
		if (result instanceof List<?>) {
			response.setResult((List<T>) result);
		} else {

			response.setResult(List.of(result));
		}

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	 public ResponseEntity<ResponseModel<T>> prepareFailedResponse1(String error) {
	       
	        response.setStatus("Failure");
	        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
	        response.setResult(null);
	        response.setError(error);
	        response.setEmail(null);

	        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	    }
	 public ResponseEntity<ResponseModel<T>> prepareFailedResponse2(String error) {
	      
	        response.setStatus("Failure");
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setResult(null);
	        response.setError(error);
	        response.setEmail(null);

	        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	    }

}
