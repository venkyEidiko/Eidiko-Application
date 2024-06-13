package com.eidiko.exception_handler;

public class SaveFailureException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaveFailureException(String message) {
        super(message);
    }
}
