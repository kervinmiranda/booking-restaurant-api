package com.boot.bookingrestaurantapi.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFountException;
import com.boot.bookingrestaurantapi.repositories.ReservationRepository;
import com.boot.bookingrestaurantapi.services.CancelReservationService;

@Service
public class CancelReservationServiceImpl implements CancelReservationService {

	private static final Logger LOOGER = LoggerFactory.getLogger(CancelReservationServiceImpl.class);

	@Autowired
	private ReservationRepository reservationRepository;

	public String deleteReservation(String locator) throws BookingException {
		
		reservationRepository.findByLocator(locator)
				.orElseThrow(() -> new NotFountException("LOCATOR_NOT_FOUND", "LOCATOR_NOT_FOUND"));
		try {
			reservationRepository.deleteByLocator(locator);
		} catch (Exception e) {
			LOOGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}

		return "LOCATOR_DELETED";
	}

}
