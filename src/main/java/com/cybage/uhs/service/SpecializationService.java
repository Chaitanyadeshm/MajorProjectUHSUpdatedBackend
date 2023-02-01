package com.cybage.uhs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.repository.SpecializationRepository;
import com.cybage.uhs.utils.ConstantMethods;
import com.cybage.uhs.utils.ConstantVars;

@Service
public class SpecializationService {
	@Autowired
	SpecializationRepository specializationRepository;
	public APIResponseEntity getAllSpiecialization(){
		return ConstantMethods.successRespone(specializationRepository.findAll(), ConstantVars.ALL_SPECIALIZATION_FETCHED_SUCCESSFULLY);
	}

}
