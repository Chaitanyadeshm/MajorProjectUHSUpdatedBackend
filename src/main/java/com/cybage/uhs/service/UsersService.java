package com.cybage.uhs.service;

import java.util.Set;

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.model.Specialization;
import com.cybage.uhs.model.Users;

public interface UsersService {

	public ApiResponseEntity addUser(Users user);

	public ApiResponseEntity addSpecialization(Long usersId, Set<Specialization> specializationsList);

	public ApiResponseEntity getAllUsersDetails();

	public ApiResponseEntity getAllUsersByUsersRole(Long usersRoleId);

	public ApiResponseEntity getAllUsersDetailsByAcountStatus(String accountStatus);
	
	public ApiResponseEntity getAllNontActiveUsers();

	public ApiResponseEntity updateUser(Users user, Long usersId);

	public String activateUsersAccount(Long usersId);

	public ApiResponseEntity getUserByUsersId(Long usersId);

	public ApiResponseEntity deleteUserById(Long usersId);

	public ApiResponseEntity verifyEmailWithOTP(String email, int userEnteredOtp);

	public ApiResponseEntity unblockUsersAccount(Long usersId);

	public ApiResponseEntity loginUserByEmail(String email);
	
	public ApiResponseEntity getAllDoctorsWithSpecialization(String specialization);

}
