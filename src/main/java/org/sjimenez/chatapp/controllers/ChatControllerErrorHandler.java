package org.sjimenez.chatapp.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class is responsible to handle the errors launched from the controllers.
 * The errors could be send like bad request because the customer didn't send
 * the necessary data to pull the requested information.
 * 
 * @author developer
 */
@ControllerAdvice
@Component
public class ChatControllerErrorHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ChatControllerErrorHandler.class);

	/**
	 * Exception Handler used on the http methods that receive a serialized class
	 * because they are using @RequestBody parameter
	 * 
	 * @param methodArgumentNotValidException
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(MethodArgumentNotValidException methodArgumentNotValidException) {
		return error(methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
				.map(FieldError::getDefaultMessage).collect(Collectors.toList()));
	}
	/**
	 * Exception Handler used on the http methods that receive parameters like Query
	 * or Path Variables
	 * 
	 * @param constraintViolaionException
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(ConstraintViolationException constraintViolaionException) {
		return error(constraintViolaionException.getConstraintViolations().stream()
				.map(constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
						constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
				.collect(Collectors.toList()));
	}

	/**
	 * Exception Handler used on the http methods that receive parameters like Query
	 * or Path Variables
	 *
	 * @param duplicateKeyException
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(DuplicateKeyException duplicateKeyException) {
		return error(duplicateKeyException.getStackTrace());
	}

	/**
	 * Exception Handler used on the http methods that receive parameters like Query
	 * or Path Variables
	 *
	 * @param entityNotFoundException
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, Object> handle(EntityNotFoundException entityNotFoundException) {
		return error(entityNotFoundException.getStackTrace());
	}


	private Map<String, Object> error(Object message) {
		LOG.warn(message.toString());
		return Collections.singletonMap("error", message);
	}
}
