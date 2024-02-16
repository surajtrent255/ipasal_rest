package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.Services.CartService;
import com.ishanitech.ipasal.ipasalwebservice.dto.CartDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
public class CartResources {
	private final Logger logger = LoggerFactory.getLogger(CartResources.class);
	private CartService cartService;

	@Autowired
	public CartResources(CartService cartService) {
		this.cartService = cartService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response<?> addToCart(@RequestBody CartDTO cartDTO) {
		try {
			Integer result = cartService.addToCart(cartDTO);
			return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wront while adding product to cart");
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{customerId}")
	public Response<?> getCaerDetailsByCustomerId(@PathVariable Integer customerId) {
		List<CartDTO> cartDetails;
		try {
			cartDetails = cartService.getCartDetailsByCustomerrId(customerId);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while getting data from database");
		}

		if (cartDetails != null) {
			return Response.ok(cartDetails, HttpStatus.OK.value(), HttpStatus.OK.name());
		} else {
			throw new ResourceNotFoundException("No cart details found for this user.");
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Response<?> updateCart(@RequestBody CartDTO cartDTO) {
		try {
			Integer result = cartService.updateCart(cartDTO);
			return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while updating cart.");
		}

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/cartId/{cartid}/customerId/{customerId}")
	public Response<?> removeFromCart(@PathVariable("cartId") Integer cartId,
			@PathVariable("customerId") Integer customerId) {
		try {
			cartService.removeFromCart(cartId, customerId);
			return Response.ok("Item removed form cart", HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while removing item from cart.");
		}
	}
}
