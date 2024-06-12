package com.eidiko.exception_handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserNotFound extends Exception {
	
	
	
	private static final long serialVersionUID = 1L;

	public UserNotFound(String message)
	{
		super(message);
	}
//	
	//String error;

}
