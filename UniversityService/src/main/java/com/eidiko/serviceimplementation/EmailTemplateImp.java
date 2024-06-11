package com.eidiko.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eidiko.entity.EmailTemplate;
import com.eidiko.repository.EmailTemplateRepo;
import com.eidiko.service.EmailTemplateInterface;
@Service
public class EmailTemplateImp implements EmailTemplateInterface{

	  @Autowired
	  private EmailTemplateRepo emailTemplateRepo;
	@Override
	public String saveEmailTemplate(EmailTemplate emailTemplate) {
		// TODO Auto-generated method stub
		
		EmailTemplate save = emailTemplateRepo.save(emailTemplate);
		if (save!=null) {
			
			return "Email template is saved....!";
		} else {
			return "Email template is not created....X";
		}
		
	}

}
