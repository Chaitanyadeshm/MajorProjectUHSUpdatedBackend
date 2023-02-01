package com.cybage.uhs.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.cybage.uhs.bean.APIResponseEntity;

@ControllerAdvice
public class GlobalExcetion {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	 @ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<APIResponseEntity> handleResourceNotFound(ResourceNotFoundException exception, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				exception.getLocalizedMessage(),
				request.getDescription(false)
				);
		APIResponseEntity errorResponse = new APIResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage(), errorMessage, false, HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);		
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<APIResponseEntity> globalException(Exception exception, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				new Date(),
				exception.getLocalizedMessage(),
				request.getDescription(false)
				);
		if(exception instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
			List<String> messages = new ArrayList<>(constraintViolations.size());
			messages.addAll((Collection<? extends String>) constraintViolations.stream()
//					.map(violation -> String.format("%s value '%s' %s", violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage()))
					.map(violation -> String.format("%s", violation.getMessage()))
					.collect(Collectors.toList()));
			
			errorMessage.setDetailedMessage(
					messages.get(0));
		}

		APIResponseEntity errorResponse = new APIResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), errorMessage, false, HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
