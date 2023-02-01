package com.cybage.uhs.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cybage.uhs.bean.APIResponseEntity;
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
import com.cybage.uhs.utils.ConstantMethods;
import com.cybage.uhs.utils.ConstantVars;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private SpecializationRepository specializationRepository;

	@Autowired
	private UsersAccountStatusRepository usersAccountStatusRepository;

	@Autowired
	private UsersRoleRepository usersRoleRepository;

	private static final Logger LOG = LogManager.getLogger(UsersService.class);

	public APIResponseEntity addUser(Users user) {
		user.setAccountCreated(new Timestamp(new Date().getTime()));
		UsersAccountStatus inActiveAccountStatus = usersAccountStatusRepository
				.findUsersAccountStatusEntityByStatus(ConstantVars.ACCOUNT_STATUS.INACTIVE.toString());
		user.setAccountStatus(inActiveAccountStatus);
		UsersRole usersRole = usersRoleRepository.findUsersRoleByRoleNameIgnoreCase(user.getUsersRole().getRoleName());
		user.setUsersRole(usersRole);
		user.setUsersOTP(null);
		user.setLoginAttempts(null);
		if (ifUserAlreadyHaveAnAccount(user.getEmail())) {
			return ConstantMethods.failureRespone(ConstantVars.USER_ALREADY_REGISTERED_WITH_THIS_ACCOUNT,
					HttpStatus.CONFLICT);
		} else {
			UsersRole doctorsUserRole = usersRoleRepository
					.findUsersRoleByRoleNameIgnoreCase(ConstantVars.USERS_ROLE.DOCTOR.toString());

			if (user.getUsersRole().getUsersRoleId() == doctorsUserRole.getUsersRoleId()) {

				Set<Specialization> specializationsList = user.getSpecialization();
				user.setSpecialization(new HashSet<Specialization>());
				Users newRegisteredDoctor = usersRepository.save(user);
				APIResponseEntity addingSpecializationResponse = addSpecialization(newRegisteredDoctor.getUsersId(),
						specializationsList);
				return addingSpecializationResponse;
			} else {
				if (isVerificationLinkSentSuccessfully(user)) {
					user.setSpecialization(new HashSet<>());
					Users newPatient = usersRepository.save(user);
					return ConstantMethods.successRespone(newPatient, ConstantVars.USER_REGISTERED_SUCCESSFULLY);
				} else {
					return ConstantMethods.failureRespone(ConstantVars.SOMETHING_WENT_WRONG);
				}
			}
		}

	}

	public APIResponseEntity addSpecialization(Long usersId, Set<Specialization> specializationsList) {

		Users doctor = usersRepository.findUsersByUsersId(usersId);
		for (Specialization specialization : specializationsList) {
			if (specialization.getSpecializationId() != null && specialization.getSpecializationId() != 0L) {
				Specialization newSpecialization = new Specialization();
				newSpecialization = specializationRepository
						.findSpecializatinoBySpecializationId(specialization.getSpecializationId());
				doctor.addSpecializataion(newSpecialization);
			} else {
				Specialization newSpecialization = specializationRepository.save(specialization);
				doctor.addSpecializataion(newSpecialization);
			}
		}
		usersRepository.save(doctor);
		Users doctorWithSpecializationAdded = usersRepository.findUsersByUsersId(usersId);
		if (isDoctorsCredentialSendOnMailSuccessfully(doctorWithSpecializationAdded)) {
			return ConstantMethods.successRespone(doctorWithSpecializationAdded,
					ConstantVars.DOCTOR_REGISTERED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(ConstantVars.DOCTOR_REGISTERATION_FAILED);
		}

	}

	public APIResponseEntity getAllUsersDetails() {
		List<Users> allUsers = usersRepository.findAllByOrderByUsersIdDesc();
		return ConstantMethods.successRespone(allUsers, ConstantVars.ALL_USERS_FETCHED_SUCCESSFULLY);
	}

	public APIResponseEntity getAllUsersByUsersRole(Long usersRoleId) {
		UsersRole usersRole = usersRoleRepository.findUsersRoleByUsersRoleId(usersRoleId);
		List<Users> specificUsersRoleUsers = usersRepository.findUsersByUsersRole(usersRole);
		if (specificUsersRoleUsers.size() > 0) {
			return ConstantMethods.successRespone(specificUsersRoleUsers,
					ConstantVars.USERS_BY_USER_ROLE_FETCHED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(ConstantVars.USERS_OF_USER_ROLE_NOT_PRESENT, HttpStatus.NO_CONTENT);
		}
	}

	public APIResponseEntity getAllUsersDetailsByAcountStatus(String accountStatus) {
		UsersAccountStatus usersAccountStatus = usersAccountStatusRepository
				.findUsersAccountStatusEntityByStatus(accountStatus);
		if (usersAccountStatus != null) {
			List<Users> usersByAccountStatus = usersRepository.findUsersByAccountStatus(usersAccountStatus);
			if (usersByAccountStatus.size() > 0) {
				return ConstantMethods.successRespone(usersByAccountStatus,
						ConstantVars.ALL_USERS_BY_ACCOUNT_STATUS_FETCHED_SUCCESSFULLY);
			} else {
				return ConstantMethods.failureRespone(ConstantVars.USERS_WITH_ACCOUNT_STATUS_ARE_NOT_PRESENT,
						HttpStatus.NO_CONTENT);
			}
		} else {
			LOG.warn(ConstantVars.ALL_USERS_BY_ACCOUNT_STATUS_FETCH_FAILED);
			return ConstantMethods.failureRespone(ConstantVars.ALL_USERS_BY_ACCOUNT_STATUS_FETCH_FAILED);
		}
	}

	private boolean isVerificationLinkSentSuccessfully(Users user) {
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

	public APIResponseEntity updateUser(Users user, Long usersId) {
		user.setUsersId(usersId);
		Users updatedUser = usersRepository.save(user);
		if (updatedUser != null) {
			return ConstantMethods.successRespone(updatedUser, ConstantVars.USER_UPDATED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(ConstantVars.USER_UPDATED_FAILED);
		}
	}

	public String activateUsersAccount(Long usersId) {

		Users user = usersRepository.findUsersByUsersId(usersId);
		if (user != null) {
			user.setAccountStatus(new UsersAccountStatus(1, null, null));
			usersRepository.save(user);
			return ConstantVars.EMAIL_VERIFIED_PAGE;
		} else {
//			return new ResourceNotFoundException(ConstantVars.ERROR_RESPONSE);
			return ConstantVars.ERROR_RESPONSE;
		}
	}

	public boolean isDoctorsCredentialSendOnMailSuccessfully(Users user) {
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

	public APIResponseEntity getUserByUsersId(Long usersId) {
		Users user = usersRepository.findUsersByUsersId(usersId);
		if (user != null) {
			return ConstantMethods.successRespone(user, ConstantVars.USERS_DATA_BY_USER_ID);
		} else {
			throw new ResourceNotFoundException(ConstantVars.ERROR_RESPONSE);
		}
	}

	public APIResponseEntity deleteUserById(Long usersId) {
		Users user = usersRepository.findUsersByUsersId(usersId);
		UsersAccountStatus deletedAccountStatus = usersAccountStatusRepository
				.findUsersAccountStatusEntityByStatus(ConstantVars.ACCOUNT_STATUS.DELETED.toString());
		UsersAccountStatus usersCurrentAccountStatus = user.getAccountStatus();
		if (usersCurrentAccountStatus.equals(deletedAccountStatus)) {
			return ConstantMethods.failureRespone(ConstantVars.USER_ALREADY_DELETED);
		} else {
			user.setAccountStatus(deletedAccountStatus);
			Users updatedUser = usersRepository.save(user);
			return ConstantMethods.successRespone(updatedUser, ConstantVars.USER_DELETED_SUCCESSFULLY);
		}
	}

	//@formatter:off
	public APIResponseEntity loginUserByEmail(String email) {
		Users user = usersRepository.findUsersByEmail(email);
		if (user != null) {
			boolean isUserActive = user.getAccountStatus().getStatus().contentEquals(ConstantVars.ACCOUNT_STATUS.ACTIVE.toString()); 
			if(isUserActive) {
				user.setUsersOTP(ConstantMethods.generateOtp());
				if (isOtpSentSuccessfully(user)) {
					usersRepository.save(user);
					return ConstantMethods.successRespone(user, ConstantVars.OTP_SENT_SUCCESSFULLY);
				} else {
					return ConstantMethods.failureRespone(ConstantVars.SOMETHING_WENT_WRONG);
				}
			}else {
				return invalidAccountStatus(user);
			}
		} else {
			return ConstantMethods.failureRespone(ConstantVars.EMAIL_NOT_REGISTERED);
		}
	}
	private APIResponseEntity invalidAccountStatus(Users user) {
		String currentAccountStatus;
		if (user.getAccountStatus().getStatus().equalsIgnoreCase(ConstantVars.ACCOUNT_STATUS.INACTIVE.toString())) {
			currentAccountStatus = ConstantVars.VERIFY_ACCOUNT_BEFORE_LOGIN;
		} else if (user.getAccountStatus().getStatus().equalsIgnoreCase(ConstantVars.ACCOUNT_STATUS.BLOCKED.toString())) {
			currentAccountStatus = ConstantVars.ACCOUNT_BLOCKED;
		} else if (user.getAccountStatus().getStatus().equalsIgnoreCase(ConstantVars.ACCOUNT_STATUS.BLOCKED_BY_ADMIN.toString())) {
			currentAccountStatus = ConstantVars.ACCOUNT_BLOCKED_BY_ADMIN;
		} else {
			currentAccountStatus = ConstantVars.ACCOUNT_DELETED;
		}
		return ConstantMethods.failureRespone(currentAccountStatus, HttpStatus.UNAUTHORIZED);
	}
	//@formatter:on

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

	public APIResponseEntity verifyEmailWithOTP(String email, int userEnteredOtp) {
		Users user = usersRepository.findUsersByEmail(email);
		if (user.getAccountStatus().getUsersAccountStatusId() == 3) {
			return ConstantMethods.failureRespone(ConstantVars.ACCOUNT_BLOCKED);
		}
		if (user.getUsersOTP() == userEnteredOtp) {
			user.setUsersOTP(null);
			Users updatedUser = usersRepository.save(user);
			return ConstantMethods.successRespone(updatedUser, ConstantVars.OTP_VERIFIED_SUCCESSFULLY);
		} else {
			int remainingAttempt;
			remainingAttempt = ConstantVars.MAX_LOGIN_ATTEMPTS_ALLOWED - 1;
			if (user.getLoginAttempts() == null) {
				user.setLoginAttempts(remainingAttempt);
				usersRepository.save(user);
				return ConstantMethods.failureRespone(ConstantVars.WRONG_OTP_ENTERED + "\nYou have " + remainingAttempt
						+ " attempt(s) to enter correct OTP.", HttpStatus.UNAUTHORIZED);
			} else if (user.getLoginAttempts() == 0) {
				user.setLoginAttempts(null);
				UsersAccountStatus activeAccountStatus = usersAccountStatusRepository
						.findUsersAccountStatusEntityByStatus(ConstantVars.ACCOUNT_STATUS.ACTIVE.toString());
				user.setAccountStatus(activeAccountStatus);
				usersRepository.save(user);
				return ConstantMethods.failureRespone(ConstantVars.MAX_OTP_ENTERED_EXCEEDED, HttpStatus.UNAUTHORIZED);
			} else {
				remainingAttempt = user.getLoginAttempts() - 1;
				user.setLoginAttempts(remainingAttempt);
				usersRepository.save(user);
				return ConstantMethods.failureRespone(ConstantVars.WRONG_OTP_ENTERED + "\nYou have " + remainingAttempt
						+ " attempts to enter correct OTP.", HttpStatus.UNAUTHORIZED);
			}
		}
	}

	public APIResponseEntity unblockUsersAccount(Long usersId) {
		Users user = usersRepository.findUsersByUsersId(usersId);
		if (user != null) {
			UsersAccountStatus activeAccountStatus = usersAccountStatusRepository
					.findUsersAccountStatusEntityByStatus(ConstantVars.ACCOUNT_STATUS.ACTIVE.toString());
			user.setAccountStatus(activeAccountStatus);
			Users activatedUserAccount = usersRepository.save(user);
			return ConstantMethods.successRespone(activatedUserAccount, ConstantVars.USER_UNBLOCKED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(ConstantVars.UNBLOCKING_USER_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public boolean ifUserAlreadyHaveAnAccount(String email) {
		if (usersRepository.existsByEmail(email)) {
			return true;
		} else {
			return false;
		}
	}

}
