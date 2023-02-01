package com.cybage.uhs.bean;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseEntity {

	private HttpStatus httpStatus;
	
	private String message;
	
	private Object data;
	
	private boolean status;
	
	private int httpStatusCode;
	
	

}
