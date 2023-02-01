package com.cybage.uhs.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
	
	private int statusCode;
	private Date timeStamp;
	private String detailedMessage;
	private String urlDescription;

}
