package com.ishanitech.ipasal.ipasalwebservice.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipasal.ipasalwebservice.Services.SocialMediaService;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.dto.SocialMediaDTO;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;


/**
 * 
 * @author Tanchhowpa
 * Sep 4, 2019, 2:13:19 PM
 *
 */

@RequestMapping("api/v1/social")
@RestController
public class SocialMediaResources {

	private SocialMediaService socialMediaService;
	
	
	 Logger logger = LoggerFactory.getLogger(SocialMediaResources.class);
	
	@Autowired
	public SocialMediaResources(SocialMediaService socialMediaService) {
		this.socialMediaService = socialMediaService;
	}
	
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Response<?> getAllSocialMedias() {
		List<SocialMediaDTO> mediaList;
		try {
			mediaList = socialMediaService.getAllSocialMedias();
			logger.info(mediaList.get(0).getSocialName());
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while getting data from database");
		}
		if(mediaList != null && mediaList.size() > 0) {
			return Response.ok(mediaList, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		throw new ResourceNotFoundException("No social medias found");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/active")
	public Response<?> getAllActiveSocialMedia() {
		List<SocialMediaDTO> mediaList;
		try {
			mediaList = socialMediaService.getAllActiveSocialMedias();
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while getting data from database");
		}
		if(mediaList != null && mediaList.size() > 0) {
			return Response.ok(mediaList, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		throw new ResourceNotFoundException("No active social medias found");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Response<?> addSocialMedia(@RequestBody SocialMediaDTO socialMediaDTO) {
		Integer result = null;
		try {
			result = socialMediaService.addSocialMedia(socialMediaDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while adding the social media");
		}
		return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{socialMediaId}")
	public Response<?> updateSocialMedia(@PathVariable("socialMediaId") Integer socialMediaId, @RequestBody SocialMediaDTO socialMedia) {
		try {
			socialMediaService.updateSocialMedia(socialMediaId, socialMedia);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Social Media Updated Successfully");
		} catch (Exception e) {
			throw new CustomSqlException("Something went wrong while updateing the social media");
		}
	}

	@RequestMapping(method = RequestMethod.GET, value="/{socialMediaId}")
	public Response<?> getSocialMediabyId(@PathVariable("socialMediaId") Integer socialMediaId) {
		SocialMediaDTO socialMedia;
		try {
			socialMedia = socialMediaService.getSocialMediaById(socialMediaId);
		} catch (Exception e) {
			throw new CustomSqlException("Something went wrong while getting the data from database");
		} if (socialMedia != null) {
			return Response.ok(Arrays.asList(socialMedia), HttpStatus.OK.value(), HttpStatus.OK.name());
		} else {
			throw new ResourceNotFoundException("No social media found");
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/setActive/{socialMediaId}")
	public Response<?> setSocialMediaActive(@PathVariable("socialMediaId") Integer socialMediaId) {
		try {
			socialMediaService.setSocialMediaActive(socialMediaId);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "The social media is set as active");
		} catch (Exception e) {
			throw new CustomSqlException("The set active operation failed");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/setInactive/{socialMediaId}")
	public Response<?> setSocialMediaInactive(@PathVariable("socialMediaId") Integer socialMediaId) {
		try {
			socialMediaService.setSocialMediaInactive(socialMediaId);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "The social media is set as Inactive");
		} catch (Exception e) {
			throw new CustomSqlException("The set Inactive operation failed");
		}
	}

}
