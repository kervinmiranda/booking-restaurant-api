package com.boot.bookingrestaurantapi.controller;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.RestaurantController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.jsons.TurnRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.RestaurantService;

public class RestaurantControllerTest {
	
	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";
	

	private static final Long RESTAURANT_ID = 1L;
	private static final String NAME = "Burguer King";
	private static final String DESCRIPTION = "Hamburguesas";
	private static final String ADDRESS = "Av. Pajaritos";
	private static final String IMAGE = "http://www.image.com/burguer";

	public static final List<TurnRest> TURN_LIST = new ArrayList<>();
	public static final RestaurantRest RESTAURANT_REST = new RestaurantRest();
	public static final List<RestaurantRest> RESTAURANT_REST_LIST = new ArrayList<>();	

	@Mock
	RestaurantService restaurantService;

	@InjectMocks
	RestaurantController restaurantController;

	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		RESTAURANT_REST.setName(NAME);
		RESTAURANT_REST.setDescription(DESCRIPTION);
		RESTAURANT_REST.setAddress(ADDRESS);
		RESTAURANT_REST.setId(RESTAURANT_ID);
		RESTAURANT_REST.setImage(IMAGE);
		RESTAURANT_REST.setTurns(TURN_LIST);		
		Mockito.when(restaurantService.getRestaurantById(RESTAURANT_ID)).thenReturn(RESTAURANT_REST);
	}

	@Test
	public void getRestaurantByIdTest() throws BookingException {
		final BookingResponse<RestaurantRest> response = restaurantController.getRestaurantById(RESTAURANT_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESTAURANT_REST);
	}
	
	@Test
	public void getRestaurantsTest() throws BookingException {
		final BookingResponse<List<RestaurantRest>> response = restaurantController.getRestaurants();
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESTAURANT_REST_LIST);		
	}
}
