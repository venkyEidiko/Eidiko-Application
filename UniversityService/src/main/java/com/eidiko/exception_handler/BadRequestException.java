package com.eidiko.exception_handler;



import lombok.NoArgsConstructor;


@NoArgsConstructor
public class BadRequestException extends RuntimeException {
	
	
	
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message)
	{
		super(message);
	}
//	
	//String error;

}
