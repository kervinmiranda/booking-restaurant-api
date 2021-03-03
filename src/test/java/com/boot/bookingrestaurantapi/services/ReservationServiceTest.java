package com.boot.bookingrestaurantapi.services;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRepository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.impl.ReservationServiceImpl;

public class ReservationServiceTest {

	private static final Long RESTAURANT_ID = 1L;
	private static final Date DATE = new Date();
	private static final Long PERSON = 1L;
	private static final Long TURNO_ID = 1L;
	private static final Long RESERVATION_ID = 5L;
	private static final String LOCATOR = "Burguer King 2";
	private static final String LOCATOR_TURN = "TURN_12_00";

	private static final String NAME = "KFC";
	private static final String DESCRIPTION = "Pollo Crispy";
	private static final String ADDRESS = "Av. Pajaritos Maipu";
	private static final String IMAGE = "www.image.com";

	private static final String TURN_NAME = "TURNO_10_00";

	CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	private static final Restaurant RESTAURANT = new Restaurant();
	private static final Reservation RESERVATION = new Reservation();
	private static final Turn TURN = new Turn();

	private static final List<Turn> TURN_LIST = new ArrayList<>();

	private static final Optional<Restaurant> OPTIONAL_RESTAURANT = Optional.of(RESTAURANT);
	private static final Optional<Restaurant> OPTIONAL_RESTAURANT_EMPTY = Optional.empty();

	private static final Optional<Turn> OPTIONAL_TURN = Optional.of(TURN);
	private static final Optional<Turn> OPTIONAL_TURN_EMPTY = Optional.empty();

	private static final Optional<Reservation> OPTIONAL_RESERVATION = Optional.of(RESERVATION);
	private static final Optional<Reservation> OPTIONAL_RESERVATION_EMPTY = Optional.empty();

	@Mock
	RestaurantRepository restaurantRepository;

	@Mock
	TurnRepository turnRepository;

	@Mock
	ReservationRepository reservationRepository;

	@InjectMocks
	ReservationServiceImpl reservationServiceImpl;

	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		RESTAURANT.setName(NAME);
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRESS);
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);

		TURN.setId(TURNO_ID);
		TURN.setName(TURN_NAME);
		TURN.setRestaurant(RESTAURANT);

		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURNO_ID);

		RESERVATION.setId(RESERVATION_ID);
		RESERVATION.setDate(DATE);
		RESERVATION.setLocator(LOCATOR);
		RESERVATION.setTurn(LOCATOR_TURN);
		RESERVATION.setPerson(PERSON);
		RESERVATION.setRestaurant(RESTAURANT);
	}

	@Test
	public void createReservationTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURNO_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
				.thenReturn(OPTIONAL_RESERVATION_EMPTY);
		Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(new Reservation());
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
	}

	@Test(expected = BookingException.class)
	public void createReservationRestaurantFindByIdTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT_EMPTY);
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}

	@Test(expected = BookingException.class)
	public void createReservationTurnFindByIdTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_TURN_EMPTY);
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}

	@Test(expected = BookingException.class)
	public void createReservationTrunAndRestaurantTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURNO_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
				.thenReturn(OPTIONAL_RESERVATION);
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}
	
	@Test(expected = BookingException.class)
	public void createReservationSaveTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURNO_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
				.thenReturn(OPTIONAL_RESERVATION_EMPTY);
		Mockito.doThrow(Exception.class).when(reservationRepository).save(Mockito.any(Reservation.class));
		//Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenThrow(new Exception());
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
	}


	

}
