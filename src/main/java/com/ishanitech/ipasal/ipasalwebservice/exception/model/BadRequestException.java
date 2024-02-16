package com.ishanitech.ipasal.ipasalwebservice.exception.model;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Yoomes <yoomesbhujel@gmail.com>
 *
 */
public class BadRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	HttpStatus status;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public BadRequestException(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}
	
}
