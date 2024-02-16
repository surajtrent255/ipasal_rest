package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.Services.PaymentService;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentResources {
	private final Logger logger = LoggerFactory.getLogger(PaymentResources.class);
	private PaymentService paymentService;

	@Autowired
	public PaymentResources(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response<?> addPaymentDetails(@RequestBody PaymentDTO paymentDTO) {
		Integer result = 0;
		try {
			result = paymentService.addPaymentDetails(paymentDTO);
			return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while adding payment details.");
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{paymentDetailsId}")
	public Response<?> getPaymentDetailsById(@PathVariable("paymentDetailsId") Integer paymentDetailsId) {
		PaymentDTO paymentDTO;
		try {
			paymentDTO = paymentService.getPaymentDetailsById(paymentDetailsId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while getting payment details from database!");
		}
		if (paymentDTO != null) {
			return Response.ok(paymentDTO, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		throw new ResourceNotFoundException("Payment Details not found.");
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{paymentDetailsId}")
	public Response<?> updatePaymentDetails(@RequestBody PaymentDTO paymentDTO) {
		Integer result = 0;
		try {
			result = paymentService.updatePaymentDetails(paymentDTO);
			return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Unable to update payment detail.");
		}

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/payStaus/{paymentDetailsId}")
	public Response<?> updatePaidStauts(@PathVariable("paymentDetailsId") Integer paymentDetailsId) {
		try {
			paymentService.updatePaidStatus(paymentDetailsId);
			return Response.ok("The payment status is changed to paid.", HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Unable to change payment status for this payment detail.");
		}
	}

}
