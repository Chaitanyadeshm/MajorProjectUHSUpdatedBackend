package com.cybage.uhs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.model.Specialization;
import com.cybage.uhs.service.SpecializationService;
import com.cybage.uhs.service.implementation.SpecializationServiceImpl;

@RestController
@RequestMapping("/specialization")
@CrossOrigin
public class SpecializationController {

	private final SpecializationService specializationService;

	@Autowired
	public SpecializationController(SpecializationServiceImpl specializationService) {
		this.specializationService = specializationService;
	}

	@GetMapping("/get-all")
	public ResponseEntity<ApiResponseEntity> getAllSpecialization() {
		ApiResponseEntity response = this.specializationService.getAllSpiecialization();
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<ApiResponseEntity> getSpecializationByCategory(@PathVariable String category){
		ApiResponseEntity specializationByCategoryResponse = this.specializationService.getByCategory(category);
		return new ResponseEntity<ApiResponseEntity>(specializationByCategoryResponse, specializationByCategoryResponse.getHttpStatus());
	}
	@PostMapping("/add")
	public ResponseEntity<ApiResponseEntity> getSpecializationByCategory(@RequestBody Specialization specialization){
		ApiResponseEntity specializationAddedResponse = this.specializationService.addSpecialization(specialization);
		return new ResponseEntity<ApiResponseEntity>(specializationAddedResponse, specializationAddedResponse.getHttpStatus());
	}
	
	
	
	
	

}
