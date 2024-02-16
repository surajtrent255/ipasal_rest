/**
 * 
 */
package com.ishanitech.ipasal.ipasalwebservice.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipasal.ipasalwebservice.Services.ToolBarMessageService;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.dto.ToolBarMessageDTO;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;

/**
 * @author Pujan K.C. <pujanov69@gmail.com>
 *
 * Created on Sep 6, 2019
 */
@RequestMapping("api/v1/toolbarMessage")
@RestController
public class ToolBarResources {

	private ToolBarMessageService toolBarMessageService;
	
	
	 Logger logger = LoggerFactory.getLogger(ToolBarResources.class);;
	
	@Autowired
	public ToolBarResources(ToolBarMessageService toolBarMessageService) {
		this.toolBarMessageService = toolBarMessageService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Response<?> getToolBarMessage() {
		ToolBarMessageDTO toolBarMessage;
		try {
			toolBarMessage = toolBarMessageService.getToolBarMessage();
			return Response.ok(toolBarMessage, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while getting data from database");
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Response<?> udateToolBarMessage(@RequestBody ToolBarMessageDTO toolBarMessageDTO) {
		ToolBarMessageDTO toolBarMessage;
		try {
			toolBarMessageService.addToolBarMessage(toolBarMessageDTO);
			return Response.ok(null , HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while updating data to database");
		}
	}
	
}
