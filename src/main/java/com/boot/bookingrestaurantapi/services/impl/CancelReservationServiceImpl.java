package com.boot.bookingrestaurantapi.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFountException;
import com.boot.bookingrestaurantapi.repositories.ReservationRepository;
import com.boot.bookingrestaurantapi.services.CancelReservationService;
import com.boot.bookingrestaurantapi.services.EmailService;

@Service
public class CancelReservationServiceImpl implements CancelReservationService {

	private static final Logger LOOGER = LoggerFactory.getLogger(CancelReservationServiceImpl.class);

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private EmailService emailService;

	public String deleteReservation(String locator) throws BookingException {

		Reservation reservation = reservationRepository.findByLocator(locator)
				.orElseThrow(() -> new NotFountException("LOCATOR_NOT_FOUND", "LOCATOR_NOT_FOUND"));
		try {
			reservationRepository.deleteByLocator(locator);
		} catch (Exception e) {
			LOOGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}

		this.emailService.processSendEmail(reservation.getEmail(), "CANCEL", reservation.getName());
		return "LOCATOR_DELETED";
	}

}
