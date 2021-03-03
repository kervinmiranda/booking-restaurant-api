package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.CancelReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.CancelReservationService;

public class CancelReservationControllerTest {

	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";

	private static final String RESERVATION_DELETED = "LOCATOR_DELETED";
	private static final String LOCATOR = "Burguer King 3";

	@Mock
	CancelReservationService CancelReservationService;

	@InjectMocks
	CancelReservationController cancelReservationController;

	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(CancelReservationService.deleteReservation(LOCATOR)).thenReturn(RESERVATION_DELETED);
	}

	@Test
	public void deleteReservationTest() throws BookingException {
		final BookingResponse<String> response = cancelReservationController.deleteReservation(LOCATOR);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESERVATION_DELETED);
	}
}
