package com.cybage.uhs.service;

import java.util.Set;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.model.Specialization;
import com.cybage.uhs.model.Users;

public interface UsersService {

	public APIResponseEntity addUser(Users user);

	public APIResponseEntity addSpecialization(Long usersId, Set<Specialization> specializationsList);

	public APIResponseEntity getAllUsersDetails();

	public APIResponseEntity getAllUsersByUsersRole(Long usersRoleId);

	public APIResponseEntity getAllUsersDetailsByAcountStatus(String accountStatus);

	public APIResponseEntity updateUser(Users user, Long usersId);

	public String activateUsersAccount(Long usersId);

	public APIResponseEntity getUserByUsersId(Long usersId);

	public APIResponseEntity deleteUserById(Long usersId);

	public APIResponseEntity verifyEmailWithOTP(String email, int userEnteredOtp);

	public APIResponseEntity unblockUsersAccount(Long usersId);

	public APIResponseEntity loginUserByEmail(String email);

}
