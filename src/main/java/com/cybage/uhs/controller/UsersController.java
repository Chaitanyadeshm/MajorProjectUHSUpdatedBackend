package com.cybage.uhs.controller;

import javax.validation.Valid;

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

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.model.Users;
import com.cybage.uhs.service.implementation.UsersServiceImpl;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {
	
	/*
	By using the constructor to inject the dependency, the object is created with all its required dependencies
	and eliminates the possibility of the dependency being changed at a later point in time.
	This ensures that the object is always in a valid state and reduces the chance of bugs and errors.

	On the other hand, using the second code can lead to a situation where the productService field is not 
	initialized and can cause NullPointerExceptions. It also makes it easier to change the productService
	dependency at a later point in time, which could lead to unintended consequences.
	*/

	private final UsersServiceImpl usersService;
	
	@Autowired
	public UsersController(UsersServiceImpl usersService) {
		this.usersService = usersService;
	}

	@GetMapping("/get-all")
	public ResponseEntity<ApiResponseEntity> getAllUsers() {
		ApiResponseEntity response = this.usersService.getAllUsersDetails();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/by-account-status/{usersAccountStatus}")
	public ResponseEntity<ApiResponseEntity> getAllUsersByRole(@PathVariable String usersAccountStatus) {
		ApiResponseEntity response = this.usersService.getAllUsersDetailsByAcountStatus(usersAccountStatus);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@GetMapping("/by-role/{usersRoleId}")
	public ResponseEntity<ApiResponseEntity> getAllUsersByUsersRole(@PathVariable Long usersRoleId) {
		ApiResponseEntity response = this.usersService.getAllUsersByUsersRole(usersRoleId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@PostMapping("/non-active")
	public ResponseEntity<ApiResponseEntity> getAllNonActiveUsers() {
		ApiResponseEntity response = this.usersService.getAllNontActiveUsers();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/get-user-by-id/{usersId}")
	public ResponseEntity<ApiResponseEntity> getUserById(@PathVariable Long usersId) {
		ApiResponseEntity respone = this.usersService.getUserByUsersId(usersId);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	@GetMapping("/users-login/{email}")
	public ResponseEntity<ApiResponseEntity> loginWithEmail(@PathVariable String email) {
		ApiResponseEntity response = this.usersService.loginUserByEmail(email);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/verify-otp/{email}/{otp}")
	public ResponseEntity<ApiResponseEntity> verifyLoginWithOTP(@PathVariable String email, @PathVariable int otp) {
		ApiResponseEntity response = this.usersService.verifyEmailWithOTP(email, otp);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/activate-account/{usersId}")
	public ResponseEntity<String> activateUsersAccount(@PathVariable Long usersId) {
		String response = this.usersService.activateUsersAccount(usersId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/add-user")
	public ResponseEntity<ApiResponseEntity> addUser(@Valid @RequestBody Users user) {
		ApiResponseEntity response = this.usersService.addUser(user);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update-user/{usersId}")
	public ResponseEntity<ApiResponseEntity> updateUser(@RequestBody Users user, @PathVariable Long usersId) {
		ApiResponseEntity response = this.usersService.updateUser(user, usersId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/delete-user/{usersId}")
	public ResponseEntity<ApiResponseEntity> deleteUserById(@PathVariable Long usersId) {
		ApiResponseEntity response = this.usersService.deleteUserById(usersId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/unblock-account/{usersId}")
	public ResponseEntity<ApiResponseEntity> unblockeUsersAccount(@PathVariable Long usersId) {
		ApiResponseEntity response = this.usersService.unblockUsersAccount(usersId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/doctor/specialization/{category}")
	public ResponseEntity<ApiResponseEntity> getAllDoctorsWithSpecialization(@PathVariable String category) {
		ApiResponseEntity response = this.usersService.getAllDoctorsWithSpecialization(category);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
