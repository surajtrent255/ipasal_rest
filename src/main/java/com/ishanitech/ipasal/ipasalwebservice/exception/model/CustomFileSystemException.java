package com.ishanitech.ipasal.ipasalwebservice.exception.model;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Yoomes <yoomesbhujel@gmail.com>
 *
 */

public class CustomFileSystemException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String message;
	private static final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	public CustomFileSystemException(String message) {
		super();
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static HttpStatus getStatus() {
		return status;
	}
	
}
