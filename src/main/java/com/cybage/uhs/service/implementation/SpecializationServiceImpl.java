package com.cybage.uhs.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.model.Specialization;
import com.cybage.uhs.repository.SpecializationRepository;
import com.cybage.uhs.service.SpecializationService;
import com.cybage.uhs.utils.ConstantMethods;
import com.cybage.uhs.utils.VariablesUtil;


@Service
public class SpecializationServiceImpl implements SpecializationService {
	private final SpecializationRepository specializationRepository;
	

	@Autowired
	public SpecializationServiceImpl(SpecializationRepository specializationRepository) {
		super();
		this.specializationRepository = specializationRepository;
	}

	@Override
	public ApiResponseEntity getAllSpiecialization() {
	List<Specialization> allSpecializations = this.specializationRepository.findAll();
		return ConstantMethods.successRespone(allSpecializations,
				VariablesUtil.ALL_SPECIALIZATION_FETCHED_SUCCESSFULLY);
	}

	@Override
	public ApiResponseEntity getByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponseEntity addSpecialization(Specialization specialization) {
		Specialization newAddedSpecialization = specializationRepository.save(specialization);
		return ConstantMethods.successRespone(newAddedSpecialization, VariablesUtil.SPECIALIZATION_ADDDED_SUCCESSFULLY);
	}

}
