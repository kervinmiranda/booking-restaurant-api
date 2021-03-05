package com.boot.bookingrestaurantapi.services;

import com.boot.bookingrestaurantapi.exceptions.BookingException;

public interface EmailService {

	public String processSendEmail(final String receiver, final String subject, String templateCode, String currentName)
			throws BookingException;

}
