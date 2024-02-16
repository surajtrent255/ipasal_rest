package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.Services.ShippingService;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.dto.ShippingDTO;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/shipping")
public class ShippingResources {
	private final Logger logger = LoggerFactory.getLogger(ShippingResources.class);
	private ShippingService shippingService;

	@Autowired
	public ShippingResources(ShippingService shippingService) {
		this.shippingService = shippingService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response<?> addShippingDetails(@RequestBody ShippingDTO shippingDTO) {
		try {
			Integer result = shippingService.addShippingDetails(shippingDTO);
			return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Unable to add shipping details.");
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{customerId}")
	public Response<?> getShippingDetailsById(@PathVariable Integer customerId) {
		ShippingDTO shippingDTO;
		try {
			shippingDTO = shippingService.getShippingDetailsById(customerId);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong whilte getting shipping details from database!");
		}

		if (shippingDTO != null) {
			return Response.ok(shippingDTO, HttpStatus.OK.value(), HttpStatus.OK.name());
		}

		throw new ResourceNotFoundException("Shipping details for customerId - " + customerId + " is not found!");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/order/{orderId}")
	public Response<?> getShippingDetailsByOrderId(@PathVariable Integer orderId){
		List<ShippingDTO> shippingDTO;
		try {
			shippingDTO = shippingService.getShippingDetailsByOrderId(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while getting shipping details from database!");
		}
		if (shippingDTO != null) {
			return Response.ok(shippingDTO, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		throw new ResourceNotFoundException("Shipping details for orderId - " + orderId + "is not found!");
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{customerId}")
	public Response<?> updateShippingDetails(@PathVariable("customerId") Integer customerId,
			@RequestBody ShippingDTO shippingDTO) {
		Integer result = 0;
		try {
			result = shippingService.updateShippingDetails(shippingDTO);
			return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException(
					"Unable to update shipping details for this customer.");
		}
	}
}
