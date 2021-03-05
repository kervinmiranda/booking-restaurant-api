package com.boot.bookingrestaurantapi.services.impl;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	private static final Logger LOOGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public String processSendEmail(String receiver, String subject, String templateCode, String currentName)
			throws BookingException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void sendEmail(final String receiver, final String subject, final String template) throws BookingException{
		final MimeMessage email = javaMailSender.createMimeMessage();
		final MimeMessageHelper content;
		
		try {
			content = new MimeMessageHelper(email, true);
			content.setSubject(subject);
			content.setTo(receiver);
			content.setText(template, true);
		} catch (Exception e) {
			LOOGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
		javaMailSender.send(email);
	}

}
