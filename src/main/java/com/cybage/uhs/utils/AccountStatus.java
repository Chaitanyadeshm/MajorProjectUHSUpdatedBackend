package com.cybage.uhs.utils;

import lombok.Getter;

@Getter
public enum AccountStatus {

//	COMPLAINT_REGISTERED_SUCCESSFULLY("");
	
	CONTINUE(100, "Continue");

	private final int id;
	
	private final String value;
	
	private AccountStatus(int id, String value) {
		this.id = id;
		this.value = value;
	}

	
	

}
