package com.eidiko.responce;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.eidiko.entity.ResponseModel;

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
}
