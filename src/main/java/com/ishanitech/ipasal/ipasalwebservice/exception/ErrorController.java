package com.ishanitech.ipasal.ipasalwebservice.exception;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomFileSystemException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.UserRegistrationException;

/**
 * 
 * @author Yoomes <yoomesbhujel@gmail.com>
 *
 */

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {
	@ExceptionHandler(UserRegistrationException.class)
	protected ResponseEntity<Response<?>> handleUserRegistrationException(UserRegistrationException ex,
			WebRequest request) {
		return new ResponseEntity<>(
				generateErrorResponseEntity(UserRegistrationException.getStatus().value(), ex.getMessage()),
				UserRegistrationException.getStatus());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Response<?>> handleIllegalArgumentException(IllegalArgumentException ex,
			WebRequest request) {
		return new ResponseEntity<>(generateErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Response<?>> handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(
				Response.ok(new ArrayList<>(), ResourceNotFoundException.getStatus().value(), ex.getMessage()),
				ResourceNotFoundException.getStatus());
	}

	/**
	 * This exception handler function handles FileSystems related exceptions
	 * 
	 * @param ex
	 * @param request
	 * @return ResponseEntity containing Response<T> class with message, body and
	 *         status code
	 */
	@ExceptionHandler(CustomFileSystemException.class)
	protected ResponseEntity<Response<?>> handleFileSystemException(CustomFileSystemException ex, WebRequest request) {
		return new ResponseEntity<>(
				generateErrorResponseEntity(CustomFileSystemException.getStatus().value(), ex.getMessage()),
				CustomFileSystemException.getStatus());
	}

	/**
	 * This exception handler function handle exceptions related to database
	 * exception Ex. query exception, query parameter exceptions
	 * 
	 * @param ex
	 * @param request
	 * @return ResponseEntity containing Response<T> class with message, body and
	 *         status code
	 */
	@ExceptionHandler(CustomSqlException.class)
	protected ResponseEntity<Response<?>> handleCustomSqlException(CustomSqlException ex, WebRequest request) {
		return new ResponseEntity<>(
				generateErrorResponseEntity(CustomSqlException.getStatus().value(), ex.getMessage()),
				CustomSqlException.getStatus());
	}

	@ExceptionHandler(MultipartException.class)
	protected ResponseEntity<Response<?>> handleMultipartException(MultipartException ex, WebRequest request) {
		return new ResponseEntity<>(
				generateErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ BadCredentialsException.class, AuthenticationException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	protected ResponseEntity<Response<?>> handleBadCredException(BadCredentialsException bex, WebRequest request) {
		return new ResponseEntity<>(Response.ok(new ArrayList<>(), HttpStatus.UNAUTHORIZED.value(), bex.getMessage()),
				HttpStatus.UNAUTHORIZED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(generateErrorResponseEntity(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage()),
				HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(
				generateErrorResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), ex.getMessage()),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(
				generateErrorResponseEntity(status.value(), "Request don't have pathvariable: " + ex.getVariableName()),
				status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(
				generateErrorResponseEntity(status.value(), "Missing request parameter " + ex.getParameterName()),
				status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(generateErrorResponseEntity(status.value(), "Not a valid request"), status);
		// return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(generateErrorResponseEntity(status.value(), "MediaType " + headers.getContentType()
				+ ", not supported! Only " + ex.getSupportedMediaTypes() + " are supported!"), status);
		// return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(generateErrorResponseEntity(status.value(), ex.getMessage()), status);
		// return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(generateErrorResponseEntity(status.value(),
				"Your request is missing requeset body or request body's format is incorrect"), status);
	}

	private static Response<?> generateErrorResponseEntity(Integer statusCode, String message) {
		return Response.ok(new ArrayList<>(), statusCode, message);
	}
}
