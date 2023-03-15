package com.cybage.uhs.service;

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.model.Specialization;

public interface SpecializationService {

	public ApiResponseEntity getAllSpiecialization();

	public ApiResponseEntity getByCategory(String category);

	public ApiResponseEntity addSpecialization(Specialization specialization);

}
