package com.cybage.uhs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.service.SpecializationService;

@RestController
@RequestMapping("/specialization")
@CrossOrigin
public class SpecializationController {

	@Autowired
	private SpecializationService specializationService;

	@GetMapping("/get-all")
	public ResponseEntity<APIResponseEntity> getAllSpecialization() {
		APIResponseEntity response = specializationService.getAllSpiecialization();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
