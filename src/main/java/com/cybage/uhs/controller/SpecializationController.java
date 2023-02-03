package com.cybage.uhs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.service.implementation.SpecializationServiceImpl;

@RestController
@RequestMapping("/specialization")
@CrossOrigin
public class SpecializationController {

	private final SpecializationServiceImpl specializationService;

	@Autowired
	public SpecializationController(SpecializationServiceImpl specializationService) {
		this.specializationService = specializationService;
	}

	@GetMapping("/get/all")
	public ResponseEntity<APIResponseEntity> getAllSpecialization() {
		APIResponseEntity response = this.specializationService.getAllSpiecialization();
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<APIResponseEntity> getSpecializationByCategory(@PathVariable String category){
		APIResponseEntity specializationByCategoryResponse = this.specializationService.getByCategory(category);
		return new ResponseEntity<APIResponseEntity>(specializationByCategoryResponse, specializationByCategoryResponse.getHttpStatus());
	}
	
	
	
	
	

}
