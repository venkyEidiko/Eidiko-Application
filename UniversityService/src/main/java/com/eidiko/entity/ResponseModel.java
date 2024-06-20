package com.eidiko.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel<T> {

	private String status;

	private Integer statusCode;

	private List<T> result;

	private String error;

	private T password;
	 
    private String email;

}
