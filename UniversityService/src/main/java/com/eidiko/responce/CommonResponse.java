package com.eidiko.responce;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.eidiko.entity.ResponseModel;
import org.springframework.stereotype.Component;

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

    public <T> ResponseEntity<ResponseModel<T>> prepareErrorResponseObject(String message, HttpStatus status) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setStatus("FAILED");
        response.setStatusCode(status.value());
        response.setResult(List.of((T) message));
        return new ResponseEntity<>(response, status);
    }
}
