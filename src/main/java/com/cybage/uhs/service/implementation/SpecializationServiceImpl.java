package com.cybage.uhs.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.model.Specialization;
import com.cybage.uhs.repository.SpecializationRepository;
import com.cybage.uhs.service.SpecializationService;
import com.cybage.uhs.utils.ConstantMethods;
import com.cybage.uhs.utils.ConstantVars;


@Service
public class SpecializationServiceImpl implements SpecializationService {
	private final SpecializationRepository specializationRepository;
	

	@Autowired
	public SpecializationServiceImpl(SpecializationRepository specializationRepository) {
		super();
		this.specializationRepository = specializationRepository;
	}

	@Override
	public APIResponseEntity getAllSpiecialization() {
	List<Specialization> allSpecializations = this.specializationRepository.findAll();
		return ConstantMethods.successRespone(allSpecializations,
				ConstantVars.ALL_SPECIALIZATION_FETCHED_SUCCESSFULLY);
	}

	@Override
	public APIResponseEntity getByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

}
