package com.boot.bookingrestaurantapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.PaymentConfirmRest;
import com.boot.bookingrestaurantapi.jsons.PaymentIntentRest;
import com.boot.bookingrestaurantapi.services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/booking-restaurant" + "/v1")
public class PaymentContoller {

	@Autowired
	PaymentService paymentService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paymentIntent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> paymentIntent(@RequestBody PaymentIntentRest paymentIntentRest)
			throws StripeException {
		PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentRest);

		String paymentString = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentString, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/confirm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> paymentConfirm(@RequestBody PaymentConfirmRest paymentConfirmRest)
			throws StripeException, BookingException {
		PaymentIntent paymentIntent = paymentService.paymentConfirm(paymentConfirmRest);

		String paymentString = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentString, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cancel"
			+ "{paymentId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> paymentConfirm(@PathVariable("paymentId") String paymentId) throws StripeException {
		PaymentIntent paymentIntent = paymentService.paymentCancel(paymentId);

		String paymentString = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentString, HttpStatus.OK);
	}

}
