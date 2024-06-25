package com.eidiko.serviceimplementation;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.eidiko.entity.EmailTemplate;
import com.eidiko.entity.Employee;

import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.repository.EmailTemplateRepo;
import com.eidiko.repository.EmployeeRepo;
import com.eidiko.service.EmailTemplateInterface;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailTemplateImp implements EmailTemplateInterface {

	@Autowired
	private EmailTemplateRepo emailTemplateRepo;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public EmailTemplate getByTemplateName(String templateName) throws UserNotFoundException {

		EmailTemplate template = emailTemplateRepo.findByNameOfTemplate(templateName)
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		return template;
	}

	
	
	// sending mail along with otp
	public String sendEmailWithOtp(String toMail)
			throws MessagingException, IOException, UserNotFoundException, RuntimeException {

		String templateName1 = "otp";

		String otp = this.generateOtp();

		// Retrieve the email template from the database
		EmailTemplate template = emailTemplateRepo.findByNameOfTemplate(templateName1)
				.orElseThrow(() -> new UserNotFoundException("Template not found"));

		Employee byEmail = employeeRepo.findByEmail(toMail)
				.orElseThrow(() -> new UserNotFoundException("user not found"));

		
		String firstName = byEmail.getFirstName();

		String lastName = byEmail.getLastName();
		String fullName = firstName + " " + lastName;
	
		// Convert Clob to String
		String body2 = template.getBody();

		if (body2 == null || body2.isEmpty()) {
			throw new IllegalArgumentException("Email body content is missing");
		}

		
		String personalizedBody = body2.replace("{{otp}}", otp).replace("{{name}}", fullName);

		// Send the email
		sendEmail(toMail, template.getSubject(), personalizedBody);
		return otp;

	}

	// random otp generation
	public String generateOtp() {
		Random random = new Random();
		int otpValue = 100000 + random.nextInt(900000);
		String otp = String.valueOf(otpValue);
		return otp;
	}

	// send mail method
	private void sendEmail(String to, String subject, String body) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(subject);

		Context context = new Context();
		context.setVariable("body", body);
		// if template name not exit than throw a runtime error along with message
		String process = templateEngine.process("MailFormat-template.html", context);
		mimeMessageHelper.setText(process, true);

		javaMailSender.send(mimeMessage);

	}

}