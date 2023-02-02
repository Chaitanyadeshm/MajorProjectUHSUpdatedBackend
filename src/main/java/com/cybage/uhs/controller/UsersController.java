package com.cybage.uhs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.model.Users;
import com.cybage.uhs.service.UsersService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {

	@Autowired
	private UsersService usersService;

	@GetMapping("/get-all-users")
	public ResponseEntity<APIResponseEntity> getAllUsers() {
		APIResponseEntity response = usersService.getAllUsersDetails();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/get-all-users-by-account-status/{usersAccountStatus}")
	public ResponseEntity<APIResponseEntity> getAllUsersByRole(@PathVariable String usersAccountStatus) {
		
		APIResponseEntity response = usersService.getAllUsersDetailsByAcountStatus(usersAccountStatus);
		
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@GetMapping("/get-all-users-by-role/{usersRoleId}")
	public ResponseEntity<APIResponseEntity> getAllUsersByUsersRole(@PathVariable Long usersRoleId) {
		APIResponseEntity response = usersService.getAllUsersByUsersRole(usersRoleId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/get-user-by-id/{usersId}")
	public ResponseEntity<APIResponseEntity> getUserById(@PathVariable Long usersId) {
		APIResponseEntity respone = usersService.getUserByUsersId(usersId);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	@GetMapping("/users-login/{email}")
	public ResponseEntity<APIResponseEntity> loginWithEmail(@PathVariable String email) {
		APIResponseEntity response = usersService.loginUserByEmail(email);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/verify-otp/{email}/{otp}")
	public ResponseEntity<APIResponseEntity> verifyLoginWithOTP(@PathVariable String email, @PathVariable int otp) {
		APIResponseEntity response = usersService.verifyEmailWithOTP(email, otp);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/activate-account/{usersId}")
	public ResponseEntity<String> activateUsersAccount(@PathVariable Long usersId) {
		String response = usersService.activateUsersAccount(usersId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	@PostMapping("/add-doctor")
//	public ResponseEntity<APIResponseEntity> addDoctor(@RequestBody Doctor doctor) {
//		APIResponseEntity response = usersService.registerDoctor(doctor);
//		return new ResponseEntity<>(response, HttpStatus.CREATED);
//	}

	@PostMapping("/add-user")
	public ResponseEntity<APIResponseEntity> addUser(@RequestBody Users user) {
		APIResponseEntity response = usersService.addUser(user);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update-user/{usersId}")
	public ResponseEntity<APIResponseEntity> updateUser(@RequestBody Users user, @PathVariable Long usersId) {
		APIResponseEntity response = usersService.updateUser(user, usersId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/delete-user/{usersId}")
	public ResponseEntity<APIResponseEntity> deleteUserById(@PathVariable Long usersId) {
		APIResponseEntity response = usersService.deleteUserById(usersId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	@GetMapping("/doctors-with-specialization/{specializationId}")
//	public ResponseEntity<APIResponseEntity> getAllDoctorsAccordingToSpecialization(
//			@PathVariable int specializationId) {
//		APIResponseEntity response = usersService.getAllDoctorsAccordingToSpecialization(specializationId);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	@GetMapping("/unblock-account/{usersId}")
	public ResponseEntity<APIResponseEntity> unblockeUsersAccount(@PathVariable Long usersId) {
		APIResponseEntity response = usersService.unblockUsersAccount(usersId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
