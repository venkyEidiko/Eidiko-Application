package com.eidiko.service;

import com.eidiko.entity.EmailTemplate;

import com.eidiko.exception_handler.UserNotFoundException;

public interface EmailTemplateInterface {
	
	
	
	
	public EmailTemplate getByTemplateName(String templateName)throws  UserNotFoundException;
	

}