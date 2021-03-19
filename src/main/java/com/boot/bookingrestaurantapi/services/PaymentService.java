package com.boot.bookingrestaurantapi.services;

import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.PaymentConfirmRest;
import com.boot.bookingrestaurantapi.jsons.PaymentIntentRest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface PaymentService {

	public PaymentIntent paymentIntent(PaymentIntentRest paymentIntentDTO) throws StripeException;

	public PaymentIntent paymentConfirm(PaymentConfirmRest paymentConfirmRest) throws StripeException, BookingException;

	public PaymentIntent paymentCancel(String paymentId) throws StripeException;
}
