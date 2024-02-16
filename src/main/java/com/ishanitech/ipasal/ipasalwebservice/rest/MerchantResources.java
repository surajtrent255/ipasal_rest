package com.ishanitech.ipasal.ipasalwebservice.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipasal.ipasalwebservice.Services.MerchantService;
import com.ishanitech.ipasal.ipasalwebservice.dto.MerchantDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;

/**
 * 
 * @author Tanchhowpa
 *
 * Apr 12, 2019 10:51:22 AM
 */


@RestController
@RequestMapping("api/v1/merchant")
public class MerchantResources {
	Logger logger = LoggerFactory.getLogger(MerchantResources.class);
	
	private MerchantService merchantService;
	
	@Autowired
	public MerchantResources(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Response<?> addMerchant(@RequestBody MerchantDTO merchantDTO) {
		Integer result = null;
		try {
			result = merchantService.addMerchant(merchantDTO);
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while getting data from database");
		}
		return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
	}

	@RequestMapping(method = RequestMethod.GET)
	public Response<?> getAllMerchants() {
		List<MerchantDTO> merchantList = null;
		try {
			merchantList = merchantService.getAllMerchants();
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while getting data from database.");
		}
		
		if(merchantList != null) {
			return Response.ok(merchantList, HttpStatus.OK.value(), HttpStatus.OK.name());
		} else {
			throw new ResourceNotFoundException("No merchants found in the database");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{merchantId}")
	public Response<?> getMerchantById(@PathVariable("merchantId") Integer merchantId) {
		MerchantDTO merchant = null;
		try {
			merchant = merchantService.getMerchantById(merchantId);
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Someting went wrong while getting data from database");
		}
		if(merchant != null) {
			return Response.ok(Arrays.asList(merchant), HttpStatus.OK.value(), HttpStatus.OK.name());
		} else {
			throw new ResourceNotFoundException("No merchant found.");
		}
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{merchantId}")
	public Response<?> updateMerchant(@PathVariable("merchantId") Integer merchantId, @RequestBody MerchantDTO merchantDTO) {
		try {
			merchantService.updateMerchant(merchantId, merchantDTO);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Merchant updated successfully!");
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while updating the merchant");
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{merchantId}")
	public Response<?> removeMerchant(@PathVariable("merchantId") Integer merchantId) {
		try {
			merchantService.removeMerchant(merchantId);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Merchant has been removed successfully");
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while deleting the merchant form the database");
		}
	}

	
	//Searching part of for merchants with search key
	@GetMapping("/search")
	public Response<?> searchMerchant(@RequestParam("searchKey") String searchKey) {
		List<MerchantDTO> searchResults = new ArrayList<>();
		try {
			searchResults = merchantService.searchMerchant(searchKey);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Something went wrong while getting data from database.");
		}

		if (searchResults != null && searchResults.size() > 0) {
			return Response.ok(searchResults, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
//		throw new ResourceNotFoundException(
//				"Couldn't find product with searchKey: " + request.getParameter("searchKey"));
		throw new ResourceNotFoundException("Could not find any merchant");
	}
}
