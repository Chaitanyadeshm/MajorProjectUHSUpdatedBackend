package com.cybage.uhs.service.implementation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.exception.ResourceNotFoundException;
import com.cybage.uhs.model.MailTemplateModel;
import com.cybage.uhs.model.Specialization;
import com.cybage.uhs.model.Users;
import com.cybage.uhs.model.UsersAccountStatus;
import com.cybage.uhs.model.UsersRole;
import com.cybage.uhs.repository.SpecializationRepository;
import com.cybage.uhs.repository.UsersAccountStatusRepository;
import com.cybage.uhs.repository.UsersRepository;
import com.cybage.uhs.repository.UsersRoleRepository;
import com.cybage.uhs.service.UsersService;
import com.cybage.uhs.utils.ConstantMethods;
import com.cybage.uhs.utils.VariablesUtil;

@Service
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;

	private final SpecializationRepository specializationRepository;

	private final UsersAccountStatusRepository usersAccountStatusRepository;

	private final UsersRoleRepository usersRoleRepository;

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, SpecializationRepository specializationRepository,
			UsersAccountStatusRepository usersAccountStatusRepository, UsersRoleRepository usersRoleRepository) {
		this.usersRepository = usersRepository;
		this.specializationRepository = specializationRepository;
		this.usersAccountStatusRepository = usersAccountStatusRepository;
		this.usersRoleRepository = usersRoleRepository;
	}

	private static final Logger LOG = LogManager.getLogger(UsersServiceImpl.class);

	@Override
	public ApiResponseEntity addUser(Users user) {

		if (checkIfUserAlreadyHaveAnAccount(user.getEmail())) {
			LOG.warn(VariablesUtil.USER_ALREADY_REGISTERED_WITH_THIS_EMAIL);
			return ConstantMethods.failureRespone(VariablesUtil.USER_ALREADY_REGISTERED_WITH_THIS_EMAIL,
					HttpStatus.CONFLICT);
		}

		user.setAccountCreated(new Timestamp(new Date().getTime()));

		UsersRole usersRole = this.usersRoleRepository
				.findUsersRoleByRoleNameIgnoreCase(user.getUsersRole().getRoleName());

		user.setUsersRole(usersRole);
		
		UsersRole doctorsRole = this.usersRoleRepository
				.findUsersRoleByRoleNameIgnoreCase(VariablesUtil.USERS_ROLE.DOCTOR.toString());
		boolean isUserTypeDoctor = usersRole.equals(doctorsRole);
//
		if (isUserTypeDoctor) {
			checkIsAllSpecializationsPresent(user.getSpecialization());
			Users registeredDoctor = this.usersRepository.save(user);
			return ConstantMethods.successRespone(registeredDoctor, VariablesUtil.USER_REGISTERED_SUCCESSFULLY);
//			return addSpecializationAndUpdateDoctor(registeredDoctor.getUsersId());

		}
//		
//		UsersAccountStatus inActiveAccountStatus = usersAccountStatusRepository
//				.findUsersAccountStatusEntityByStatus(VariablesUtil.ACCOUNT_STATUS.INACTIVE.toString());
//		user.setAccountStatus(inActiveAccountStatus);
//
//		if (isAccountActivationLinkSentSuccessfully(user)) {
//			Users newPatient = this.usersRepository.save(user);
		return ConstantMethods.successRespone(user, VariablesUtil.USER_REGISTERED_SUCCESSFULLY);
//		} else {
//			return ConstantMethods.failureRespone(VariablesUtil.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
//		}

	}

	private ApiResponseEntity addSpecializationAndUpdateDoctor(Long userId) {
		
		Users user = this.usersRepository.findUsersByUsersId(userId);
		Set<Specialization> specializationsList = user.getSpecialization();
//		Set<Object> newSpecializationList = new HashSet<Object>();
		System.out.println("old specialization set: " + specializationsList);
		for (Specialization specialization : specializationsList) {

			Specialization specializationObject = new Specialization();

			specializationObject = this.specializationRepository
					.findSpecializationBySpecializationId(specialization.getSpecializationId());

			user.addSpecializataion(specializationObject);
		}
		Users registeredDoctorWithSpecialization = this.usersRepository.save(user);
//		System.out.println("new specialization set: " + newSpecializationList);

		return ConstantMethods.successRespone(registeredDoctorWithSpecialization,
				VariablesUtil.DOCTOR_REGISTERED_SUCCESSFULLY);

	}

	private void checkIsAllSpecializationsPresent(Set<Specialization> specializationsList) {
		for (Specialization specialization : specializationsList) {
			try {
				Specialization specializationObject = new Specialization();

				specializationObject = this.specializationRepository
						.findSpecializationBySpecializationId(specialization.getSpecializationId());
				if (specializationObject == null) {
					throw new ResourceNotFoundException("Cannot find selected specialization(s).");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ResourceNotFoundException(
						"Cannot find selected specialization with id: " + specialization.getSpecializationId());
			}
		}

	}

	@Override
	public ApiResponseEntity addSpecialization(Long usersId, Set<Specialization> specializationsList) {

		Users doctor = this.usersRepository.findUsersByUsersId(usersId);
		for (Specialization specialization : specializationsList) {
			try {

				Specialization newSpecialization = new Specialization();
				newSpecialization = this.specializationRepository
						.findSpecializationBySpecializationId(specialization.getSpecializationId());

				doctor.addSpecializataion(newSpecialization);
			} catch (Exception e) {
				this.usersRepository.deleteById(usersId);
				e.printStackTrace();
				throw new ResourceNotFoundException("Cannot find selected specialization(s).");
			}
		}

		Users doctorWithSpecializationAdded = this.usersRepository.findUsersByUsersId(usersId);
		Users user = this.usersRepository.save(doctorWithSpecializationAdded);
		if (isDoctorsCredentialSendOnMailSuccessfully(user)) {
			return ConstantMethods.successRespone(user, VariablesUtil.DOCTOR_REGISTERED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(VariablesUtil.DOCTOR_REGISTERATION_FAILED);
		}

	}

	@Override
	public ApiResponseEntity getAllUsersDetails() {
		List<Users> allUsers = this.usersRepository.findAllByOrderByUsersIdDesc();
		return ConstantMethods.successRespone(allUsers, VariablesUtil.ALL_USERS_FETCHED_SUCCESSFULLY);
	}

	@Override
	public ApiResponseEntity getAllUsersByUsersRole(Long usersRoleId) {
		UsersRole usersRole = this.usersRoleRepository.findUsersRoleByUsersRoleId(usersRoleId);
		List<Users> specificUsersRoleUsers = this.usersRepository.findUsersByUsersRole(usersRole);
		if (specificUsersRoleUsers.size() > 0) {
			return ConstantMethods.successRespone(specificUsersRoleUsers,
					VariablesUtil.USERS_BY_USER_ROLE_FETCHED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(VariablesUtil.USERS_OF_USER_ROLE_NOT_PRESENT, HttpStatus.NO_CONTENT);
		}
	}

	@Override
	public ApiResponseEntity getAllUsersDetailsByAcountStatus(String accountStatus) {
		UsersAccountStatus usersAccountStatus = usersAccountStatusRepository
				.findUsersAccountStatusEntityByStatus(accountStatus);
		if (usersAccountStatus != null) {
			List<Users> usersByAccountStatus = this.usersRepository.findUsersByAccountStatus(usersAccountStatus);
			if (usersByAccountStatus.size() > 0) {
				return ConstantMethods.successRespone(usersByAccountStatus,
						VariablesUtil.ALL_USERS_BY_ACCOUNT_STATUS_FETCHED_SUCCESSFULLY);
			} else {
				return ConstantMethods.failureRespone(VariablesUtil.USERS_WITH_ACCOUNT_STATUS_ARE_NOT_PRESENT,
						HttpStatus.OK);
			}
		} else {
			LOG.warn(VariablesUtil.ALL_USERS_BY_ACCOUNT_STATUS_FETCH_FAILED);
			return ConstantMethods.failureRespone(VariablesUtil.ALL_USERS_BY_ACCOUNT_STATUS_FETCH_FAILED);
		}
	}

	@Override
	public ApiResponseEntity getAllNontActiveUsers() {
		UsersAccountStatus activeAccountStatus = usersAccountStatusRepository
				.findUsersAccountStatusEntityByStatus(VariablesUtil.ACCOUNT_STATUS.ACTIVE.toString());
		if (activeAccountStatus != null) {
			List<Users> usersByAccountStatus = this.usersRepository
					.findUsersByAccountStatusNotLike(activeAccountStatus);
			if (usersByAccountStatus.size() > 0) {
				return ConstantMethods.successRespone(usersByAccountStatus,
						VariablesUtil.ALL_USERS_BY_ACCOUNT_STATUS_FETCHED_SUCCESSFULLY);
			} else {
				return ConstantMethods.failureRespone(VariablesUtil.USERS_WITH_ACCOUNT_STATUS_ARE_NOT_PRESENT,
						HttpStatus.OK);
			}
		} else {
			LOG.warn(VariablesUtil.ALL_USERS_BY_ACCOUNT_STATUS_FETCH_FAILED);
			return ConstantMethods.failureRespone(VariablesUtil.ALL_USERS_BY_ACCOUNT_STATUS_FETCH_FAILED);
		}
	}

	private boolean isAccountActivationLinkSentSuccessfully(Users user) {
		MailTemplateModel accountVerificationLinkMailTemplate = new MailTemplateModel();
		accountVerificationLinkMailTemplate.setUser(user);
		accountVerificationLinkMailTemplate.setMailSubject("OTP for Sign In to your account ");
		accountVerificationLinkMailTemplate.setMailBody("<p style='font-size:1.1em'>Hi, " + user.getFirstname() + " "
				+ user.getLastname() + ",</p>"
				+ "    <p>Thank you for choosing Universal Health Services. Use the following button to verify your account before loggin in.</p>"
				+ "    <h2 style='background: #73c1d4;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;'>"
				+ "    <a href='http://localhost:8085/users/activate-account/" + user.getUsersId()
				+ "'> Verify Your Account <a></h2>");

		return ConstantMethods.isMailSentSuccessfully(accountVerificationLinkMailTemplate);
	}

	@Override
	public ApiResponseEntity updateUser(Users user, Long usersId) {
		user.setUsersId(usersId);
		Users updatedUser = this.usersRepository.save(user);
		if (updatedUser != null) {
			return ConstantMethods.successRespone(updatedUser, VariablesUtil.USER_UPDATED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(VariablesUtil.USER_UPDATED_FAILED);
		}
	}

	@Override
	public String activateUsersAccount(Long usersId) {

		Users user = this.usersRepository.findUsersByUsersId(usersId);
		if (user != null) {
			user.setAccountStatus(new UsersAccountStatus(1, null, null));
			this.usersRepository.save(user);
			return VariablesUtil.EMAIL_VERIFIED_PAGE;
		} else {
//			return new ResourceNotFoundException(ConstantVars.ERROR_RESPONSE);
			return VariablesUtil.ERROR_RESPONSE;
		}
	}

	private boolean isDoctorsCredentialSendOnMailSuccessfully(Users user) {
		MailTemplateModel doctorsCredentialMail = new MailTemplateModel();
		doctorsCredentialMail.setUser(user);
		doctorsCredentialMail.setMailSubject("Login Credentials");
		//@formatter:off
		doctorsCredentialMail.setMailBody(
	      		  "    <p style='font-size:1.1em'>Hi, " + user.getFirstname() + " "+ user.getLastname() + ",</p>"
				+ "    <p>Thank you for choosing UHS. Use below email to login to your account. For any query write email to admin@cybage.com</p>"
				+ "    <h2 style='background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;'>"
				+      user.getEmail() 
				+ "    </h2>"
				);
		//@formatter:on
		return ConstantMethods.isMailSentSuccessfully(doctorsCredentialMail);
	}

	@Override
	public ApiResponseEntity getUserByUsersId(Long usersId) {
		Users user = this.usersRepository.findUsersByUsersId(usersId);
		if (user != null) {
			return ConstantMethods.successRespone(user, VariablesUtil.USERS_DATA_BY_USER_ID);
		} else {
			throw new ResourceNotFoundException(VariablesUtil.ERROR_RESPONSE);
		}
	}

	@Override
	public ApiResponseEntity deleteUserById(Long usersId) {
		Users user = this.usersRepository.findUsersByUsersId(usersId);
		UsersAccountStatus deletedAccountStatus = usersAccountStatusRepository
				.findUsersAccountStatusEntityByStatus(VariablesUtil.ACCOUNT_STATUS.DELETED.toString());
		UsersAccountStatus usersCurrentAccountStatus = user.getAccountStatus();
		if (usersCurrentAccountStatus.equals(deletedAccountStatus)) {
			return ConstantMethods.failureRespone(VariablesUtil.USER_ALREADY_DELETED);
		} else {
			user.setAccountStatus(deletedAccountStatus);
			Users updatedUser = this.usersRepository.save(user);
			return ConstantMethods.successRespone(updatedUser, VariablesUtil.USER_DELETED_SUCCESSFULLY);
		}
	}

	@Override
	public ApiResponseEntity loginUserByEmail(String email) {
		Users user = this.usersRepository.findUsersByEmail(email);
		if (user != null) {
			boolean isUserActive = user.getAccountStatus().getStatus()
					.contentEquals(VariablesUtil.ACCOUNT_STATUS.ACTIVE.toString());
			if (isUserActive) {
				user.setUsersOTP(ConstantMethods.generateOtp());
				System.out.println("new otp: " + user.getUsersOTP());

				if (isOtpSentSuccessfully(user)) {
					this.usersRepository.save(user);
					return ConstantMethods.successRespone(user, VariablesUtil.OTP_SENT_SUCCESSFULLY);
				} else {
					return ConstantMethods.failureRespone(VariablesUtil.SOMETHING_WENT_WRONG);
				}
			} else {
				return inactiveUser(user);
			}
		} else {
			return ConstantMethods.failureRespone(VariablesUtil.EMAIL_NOT_REGISTERED);
		}
	}

	private ApiResponseEntity inactiveUser(Users user) {
		String currentAccountStatus;
		if (user.getAccountStatus().getStatus().equalsIgnoreCase(VariablesUtil.ACCOUNT_STATUS.INACTIVE.toString())) {
			currentAccountStatus = VariablesUtil.VERIFY_ACCOUNT_BEFORE_LOGIN;
		} else if (user.getAccountStatus().getStatus()
				.equalsIgnoreCase(VariablesUtil.ACCOUNT_STATUS.BLOCKED.toString())) {
			currentAccountStatus = VariablesUtil.ACCOUNT_BLOCKED;
		} else if (user.getAccountStatus().getStatus()
				.equalsIgnoreCase(VariablesUtil.ACCOUNT_STATUS.BLOCKED_BY_ADMIN.toString())) {
			currentAccountStatus = VariablesUtil.ACCOUNT_BLOCKED_BY_ADMIN;
		} else {
			currentAccountStatus = VariablesUtil.ACCOUNT_DELETED;
		}
		return ConstantMethods.failureRespone(currentAccountStatus, HttpStatus.UNAUTHORIZED);
	}

	private boolean isOtpSentSuccessfully(Users user) {
		MailTemplateModel otpMailTemplateModel = new MailTemplateModel();
		otpMailTemplateModel.setUser(user);
		otpMailTemplateModel.setMailSubject("OTP for Sign In to your account ");
		otpMailTemplateModel.setMailBody("    <p style='font-size:1.1em'>Hi,</p>"
				+ "    <p>Thank you for choosing UHS. Use below OTP to login to your account.</p>"
				+ "    <h2 style='background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;'>"
				+ user.getUsersOTP() + "</h2>");
		return ConstantMethods.isMailSentSuccessfully(otpMailTemplateModel);
	}

	@Override
	public ApiResponseEntity verifyEmailWithOTP(String email, int userEnteredOtp) {
		Users user = this.usersRepository.findUsersByEmail(email);
		if (user.getAccountStatus().getUsersAccountStatusId() == 3) {
			return ConstantMethods.failureRespone(VariablesUtil.ACCOUNT_BLOCKED);
		}
		if (user.getUsersOTP() == userEnteredOtp) {
			user.setUsersOTP(null);
			Users updatedUser = this.usersRepository.save(user);
			return ConstantMethods.successRespone(updatedUser, VariablesUtil.OTP_VERIFIED_SUCCESSFULLY);
		} else {
			int remainingAttempt;
			remainingAttempt = VariablesUtil.MAX_LOGIN_ATTEMPTS_ALLOWED - 1;
			if (user.getLoginAttempts() == null) {
				user.setLoginAttempts(remainingAttempt);
				this.usersRepository.save(user);
				return ConstantMethods.failureRespone(VariablesUtil.WRONG_OTP_ENTERED + "\nYou have " + remainingAttempt
						+ " attempt(s) to enter correct OTP.", HttpStatus.UNAUTHORIZED);
			} else if (user.getLoginAttempts() == 0) {
				user.setLoginAttempts(null);
				UsersAccountStatus activeAccountStatus = usersAccountStatusRepository
						.findUsersAccountStatusEntityByStatus(VariablesUtil.ACCOUNT_STATUS.ACTIVE.toString());
				user.setAccountStatus(activeAccountStatus);
				this.usersRepository.save(user);
				return ConstantMethods.failureRespone(VariablesUtil.MAX_OTP_ENTERED_EXCEEDED, HttpStatus.UNAUTHORIZED);
			} else {
				remainingAttempt = user.getLoginAttempts() - 1;
				user.setLoginAttempts(remainingAttempt);
				this.usersRepository.save(user);
				return ConstantMethods.failureRespone(VariablesUtil.WRONG_OTP_ENTERED + "\nYou have " + remainingAttempt
						+ " attempts to enter correct OTP.", HttpStatus.UNAUTHORIZED);
			}
		}
	}

	@Override
	public ApiResponseEntity unblockUsersAccount(Long usersId) {
		Users user = this.usersRepository.findUsersByUsersId(usersId);
		if (user != null) {
			UsersAccountStatus activeAccountStatus = usersAccountStatusRepository
					.findUsersAccountStatusEntityByStatus(VariablesUtil.ACCOUNT_STATUS.ACTIVE.toString());
			user.setAccountStatus(activeAccountStatus);
			Users activatedUserAccount = this.usersRepository.save(user);
			return ConstantMethods.successRespone(activatedUserAccount, VariablesUtil.USER_UNBLOCKED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(VariablesUtil.UNBLOCKING_USER_FAILED,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean checkIfUserAlreadyHaveAnAccount(String email) {
		if (this.usersRepository.existsByEmail(email)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ApiResponseEntity getAllDoctorsWithSpecialization(String specialization) {
		UsersRole doctorsRole = usersRoleRepository
				.findUsersRoleByRoleNameIgnoreCase(VariablesUtil.USERS_ROLE.DOCTOR.toString());
		Specialization specializationObject = specializationRepository.findSpecializatinoByCategory(specialization);
		List<Specialization> specializations = new ArrayList<Specialization>();
		specializations.add(specializationObject);
		List<Users> doctorsWithSpecialization = usersRepository.findUsersByUsersRoleAndSpecializationIn(doctorsRole,
				specializations);
		if (doctorsWithSpecialization.size() > 0) {
			return ConstantMethods.successRespone(doctorsWithSpecialization,
					"Doctors with specialization category fetched successfully.");
		} else {
			return ConstantMethods.failureRespone("No doctor is available with specialization");
		}

	}

}
