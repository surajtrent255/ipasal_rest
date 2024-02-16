package com.ishanitech.ipasal.ipasalwebservice.exception.model;

import org.springframework.http.HttpStatus;

/**
 * @author Yoomes <yoomesbhujel@gmail.com>
 */
public class CustomSqlException extends RuntimeException {
	String message;
	private final static HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	private static final long serialVersionUID = 1L;
	public CustomSqlException(String message) {
		super(message);
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
