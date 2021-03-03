package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.boot.bookingrestaurantapi.controllers.ReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.ReservationService;

public class ReservationControllerTest {

	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";

	private static final Long RESTAURANT_ID = 1L;
	private static final Date DATE = new Date();
	private static final Long PERSON = 1L;
	private static final Long TURNO = 1L;
	private static final String LOCATOR = "Burguer King 2";

	CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();

	public static ReservationRest RESERVATION_REST = new ReservationRest();

	public static final ModelMapper modelMapper = new ModelMapper();

	@Mock
	ReservationService reservationService;

	@InjectMocks
	ReservationController reservationController;

	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURNO);
		Mockito.when(reservationService.createReservation(CREATE_RESERVATION_REST)).thenReturn(LOCATOR);
		RESERVATION_REST = modelMapper.map(CREATE_RESERVATION_REST, ReservationRest.class);
		Mockito.when(reservationService.getReservation(RESTAURANT_ID)).thenReturn(RESERVATION_REST);
	}

	@Test
	public void getReservationByIdTest() throws BookingException {
		final BookingResponse<ReservationRest> response = reservationController.getReservationById(RESTAURANT_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESERVATION_REST);
	}

	@Test
	public void createReservationTest() throws BookingException {
		final BookingResponse<String> response = reservationController.createReservation(CREATE_RESERVATION_REST);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LOCATOR);
	}
}
