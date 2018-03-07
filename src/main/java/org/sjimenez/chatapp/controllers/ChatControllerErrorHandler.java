package org.sjimenez.chatapp.controllers;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Component
public class ChatControllerErrorHandler {
	
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(MethodArgumentNotValidException methodArgumentNotValidException) {
		return error(methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
				.map(FieldError::getDefaultMessage).collect(Collectors.toList()));
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(ConstraintViolationException constraintViolaionException) {
		return error(constraintViolaionException.getConstraintViolations().stream().map( constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
                constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
				.collect(Collectors.toList()));
	}

	private Map<String, Object> error(Object message) {
		return Collections.singletonMap("error", message);
	}
}
