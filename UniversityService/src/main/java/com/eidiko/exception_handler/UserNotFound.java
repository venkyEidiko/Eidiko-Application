package com.eidiko.exception_handler;



import lombok.NoArgsConstructor;


@NoArgsConstructor
public class UserNotFound extends RuntimeException {
	
	
	
	private static final long serialVersionUID = 1L;

	public UserNotFound(String message)
	{
		super(message);
	}
//	
	//String error;

}
