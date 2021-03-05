package com.boot.bookingrestaurantapi.services.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFountException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRepository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.EmailService;
import com.boot.bookingrestaurantapi.services.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	private static final Logger LOOGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private TurnRepository turnRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private EmailService emailService;

	public static final ModelMapper modelMapper = new ModelMapper();

	public ReservationRest getReservation(Long reservationId) throws BookingException {
		return modelMapper.map(getReservationEntity(reservationId), ReservationRest.class);
	}

	public String createReservation(CreateReservationRest createReservationRest) throws BookingException {

		final Restaurant restaurantId = restaurantRepository.findById(createReservationRest.getRestaurantId())
				.orElseThrow(() -> new NotFountException("RESTAURANT_NOT_FOUND", "RESTAURANT_NOT_FOUND"));

		final Turn turn = turnRepository.findById(createReservationRest.getTurnId())
				.orElseThrow(() -> new NotFountException("TURN_NOT_FOUND", "TURN_NOT_FOUND"));

		if (reservationRepository.findByTurnAndRestaurantId(turn.getName(), restaurantId.getId()).isPresent()) {
			throw new NotFountException("RESERVATION_ALREADT_EXIST", "RESERVATION_ALREADT_EXIST");
		}

		String locator = generateLocator(restaurantId, createReservationRest);

		final Reservation reservation = new Reservation();
		reservation.setLocator(locator);
		reservation.setPerson(createReservationRest.getPerson());
		reservation.setDate(createReservationRest.getDate());
		reservation.setRestaurant(restaurantId);
		reservation.setTurn(turn.getName());
		reservation.setName(createReservationRest.getName());
		reservation.setEmail(createReservationRest.getEmail());

		try {
			reservationRepository.save(reservation);
		} catch (final Exception e) {
			LOOGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}

		this.emailService.processSendEmail(createReservationRest.getEmail(), "RESERVATION",
				createReservationRest.getName());

		return locator;
	}

	private String generateLocator(Restaurant restaurantId, CreateReservationRest createReservationRest)
			throws BookingException {
		return restaurantId.getName() + createReservationRest.getTurnId();
	}

	private Reservation getReservationEntity(Long reservationId) throws BookingException {
		return reservationRepository.findById(reservationId)
				.orElseThrow(() -> new NotFountException("SNOT-404-2", "RESERVATION_NOT_FOUND"));
	}

}
