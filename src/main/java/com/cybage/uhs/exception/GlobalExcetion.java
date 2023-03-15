package com.cybage.uhs.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cybage.uhs.bean.ApiResponseEntity;

@ControllerAdvice
public class GlobalExcetion  {
	
//	@Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        String message = "The requested resource was not found";
//        System.out.println("called");
//        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
//    }
	
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResponseEntity> handleResourceNotFound(ResourceNotFoundException exception,
			WebRequest request) {
		ExceptionResponse errorMessage = new ExceptionResponse(new Date(), exception.getLocalizedMessage(),
				request.getDescription(false));

		ApiResponseEntity errorResponse = new ApiResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage(),
				errorMessage, false, HttpStatus.NOT_FOUND.value());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler(NoHandlerFoundException.class)
//	@ResponseStatus(value = HttpStatus.NOT_FOUND)
//	public ResponseEntity<ApiResponseEntity> handleUrlNotFoundException(NoHandlerFoundException exception,
//			WebRequest request) {
//
//		ExceptionResponse exceptinResponse = new ExceptionResponse(new Date(), exception.getLocalizedMessage(),
//				request.getDescription(false));
//
//		ApiResponseEntity errorResponse = new ApiResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage(),
//				exceptinResponse, false, HttpStatus.NOT_FOUND.value());
//
//		return new ResponseEntity<ApiResponseEntity>(errorResponse, HttpStatus.NOT_FOUND);
//	}
	@ExceptionHandler(ConstraintViolationException.class )
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleInvalidInputException(InvalidInput exception, WebRequest request) {
		System.out.println("called 2");
		ExceptionResponse errorMessage = new ExceptionResponse();
		
		errorMessage = new ExceptionResponse(new Date(), exception.getLocalizedMessage(),
				request.getDescription(false));
		
		Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception)
				.getConstraintViolations();
		
		List<String> messages = new ArrayList<>(constraintViolations.size());
		
		messages.addAll((Collection<? extends String>) constraintViolations.stream()
				.map(violation -> String.format("%s value '%s'",violation.getPropertyPath(), violation.getMessage())).collect(Collectors.toList()));
		errorMessage.setDetailedMessage(messages.get(0));
		
		ApiResponseEntity errorResponse = new ApiResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage(), errorMessage, false, HttpStatus.INTERNAL_SERVER_ERROR.value());

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
//	@ExceptionHandler(Exception.class)
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	public ResponseEntity<ApiResponseEntity> globalException(Exception exception, WebRequest request) {
//		System.out.println("called");
//		
//		ExceptionResponse errorMessage = new ExceptionResponse();
//		errorMessage = new ExceptionResponse(new Date(), exception.getLocalizedMessage(),
//				request.getDescription(false));
//		if (exception instanceof ConstraintViolationException) {
////		handleInvalidInputException(exception, request);
//			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception)
//					.getConstraintViolations();
//			List<String> messages = new ArrayList<>(constraintViolations.size());
//			messages.addAll((Collection<? extends String>) constraintViolations.stream()
////					.map(violation -> String.format("%s value '%s' %s", violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage())));
//					.map(violation -> String.format("%s", violation.getPropertyPath())).collect(Collectors.toList()));
//
//			errorMessage.setDetailedMessage(messages.get(0));
//		}
//
//		exception.printStackTrace();
//		ApiResponseEntity errorResponse = new ApiResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
//				exception.getMessage(), errorMessage, false, HttpStatus.INTERNAL_SERVER_ERROR.value());
//
//		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	

	@ExceptionHandler(value = { NotFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
		return new ResponseEntity<>("Resource not founddddd", HttpStatus.NOT_FOUND);
	}
	
	

}
