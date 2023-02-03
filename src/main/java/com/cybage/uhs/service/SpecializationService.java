package com.cybage.uhs.service;

import com.cybage.uhs.bean.APIResponseEntity;

public interface SpecializationService {

	public APIResponseEntity getAllSpiecialization();

	public APIResponseEntity getByCategory(String category);

}
