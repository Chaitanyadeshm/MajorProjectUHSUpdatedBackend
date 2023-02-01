package com.cybage.uhs.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.model.MailTemplateModel;
import com.cybage.uhs.model.Users;
import com.cybage.uhs.model.UsersAccountStatus;
import com.cybage.uhs.model.UsersComplaint;
import com.cybage.uhs.repository.UsersAccountStatusRepository;
import com.cybage.uhs.repository.UsersComplaintRepository;
import com.cybage.uhs.repository.UsersRepository;
import com.cybage.uhs.utils.ConstantMethods;
import com.cybage.uhs.utils.ConstantVars;

@Service
public class UsersComplaintService {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private UsersComplaintRepository usersComplaintRepository;
	@Autowired
	private UsersAccountStatusRepository usersAccountStatusRepository;

	public APIResponseEntity getAllComplaints() {
		List<UsersComplaint> allComplaints = usersComplaintRepository.findAll();
		return ConstantMethods.successRespone(allComplaints, "All complaints fetched successfully.");
	}

	public APIResponseEntity getComplaintById(Long usersComplaintId) {
		UsersComplaint complaint = usersComplaintRepository.findByUsersComplaintId(usersComplaintId);
		return ConstantMethods.successRespone(complaint,
				"Complaint with id " + usersComplaintId + " fetched successfully.");
	}

	public APIResponseEntity addComplaint(UsersComplaint usersComplaint) {
		usersComplaint.setComplaintStatus(ConstantVars.COMPLAINT_STATUS.UNRESOLVED.toString());
		usersComplaint.setCreatedTime(new Timestamp(new Date().getTime()));
		usersComplaint.setReminderSent(0);
		Users usersDetails = usersRepository.findUsersByUsersId(usersComplaint.getUsersDetails().getUsersId());
		usersComplaint.setUsersDetails(usersDetails);
		UsersComplaint newPatientComplaints = usersComplaintRepository.save(usersComplaint);
		return sendComplaintDetailsOnMail(newPatientComplaints);
//		return ConstantMethods.successRespone(newPatientComplaints, ConstantVars.COMPLAINT_REGISTERED_SUCCESSFULLY);
	}

	private APIResponseEntity sendComplaintDetailsOnMail(UsersComplaint complaint) {
		MailTemplateModel newRegisteredComplaintMailBody = new MailTemplateModel();
		newRegisteredComplaintMailBody.setUser(complaint.getUsersDetails());
		newRegisteredComplaintMailBody.setMailSubject("Complaint Details");
		// @formatter:off
		String newComplaintBody = 
				  "    <p>Your complaint has been registered. "
				+ " 	We are sorry for the inconvenience.</p>"
				+ "    <p> Please find below the details of your complaint:</p>" 
				+ "		Description: " + complaint.getComplaintDescription() + "</br>" 
				+ "		Type: " + complaint.getComplaintType()+ "</br> " 
				+ "		Status: " + complaint.getComplaintStatus() + "</br> " 
				+ "		Created on: "+ complaint.getCreatedTime()+ "     <br/> ";
		// @formatter:on
		newRegisteredComplaintMailBody.setMailBody(newComplaintBody);
		if (ConstantMethods.isMailSentSuccessfully(newRegisteredComplaintMailBody)) {
			return ConstantMethods.successRespone(complaint, ConstantVars.COMPLAINT_REGISTERED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(ConstantVars.COMPLAINT_REGISTERATION_FAILED);
		}

	}

	public APIResponseEntity updateComplaint(UsersComplaint complaint, Long usersComplaintId) {

		Users usersDetails = usersRepository.findUsersByUsersId(usersComplaintId);
		complaint.setUsersDetails(usersDetails);
		complaint.setUsersComplaintId(usersComplaintId);
		complaint.setComplaintStatus(ConstantVars.COMPLAINT_STATUS.RESOLVED.toString());
		UsersComplaint usersComplaint = usersComplaintRepository.save(complaint);
		return sendComplaintUpdatesOnMail(usersComplaint);
	}

	private APIResponseEntity sendComplaintUpdatesOnMail(UsersComplaint complaint) {

		//@formatter:off
		String complaintUpdatesMailBody = 
				  "    <p style='font-size:1.1em'>Hi " + complaint.getUsersDetails().getFirstname() + " " + complaint.getUsersDetails().getLastname() +",</p>" 
				+ "    <p> Your complaint has been resolved. "
				+ " 	We are sorry for the inconvenience.</p>"
				+ "    <p> Please find below your complaint's details:</p>" 
				+ "		Description: " + complaint.getComplaintDescription() + "</br>" 
				+ "		Type: " + complaint.getComplaintType() + "</br> " 
				+ "		Updated Status: " + complaint.getComplaintStatus() + "</br> " 
				+ "		Response: " + complaint.getAdminReply() + "</br> " 
				+ "		Created on: " + complaint.getCreatedTime();
		// @formatter:on
		MailTemplateModel updatesOnComplaintMailTemplate = new MailTemplateModel();
		updatesOnComplaintMailTemplate.setMailSubject("Updates on your Complaint");
		updatesOnComplaintMailTemplate.setUser(complaint.getUsersDetails());
		updatesOnComplaintMailTemplate.setMailBody(complaintUpdatesMailBody);
		if (ConstantMethods.isMailSentSuccessfully(updatesOnComplaintMailTemplate)) {
			return ConstantMethods.successRespone(complaint, ConstantVars.COMPLAINT_UPDATED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(ConstantVars.COMPLAINT_UPDATION_FAILED);
		}
	}

	public APIResponseEntity getComplaintsUsersById(Long usersId) {
		Users userDetails = usersRepository.findUsersByUsersId(usersId);
		List<UsersComplaint> allComplaintsPatientId = usersComplaintRepository.findByUsersDetails(userDetails);
		return ConstantMethods.successRespone(allComplaintsPatientId, "All complaints for user fetched successfully.");
	}

	public APIResponseEntity sendReminder(Long usersComplaintId) {
		UsersComplaint complaint = usersComplaintRepository.findByUsersComplaintId(usersComplaintId);
		UsersAccountStatus userAccountStatus = usersAccountStatusRepository
				.findUsersAccountStatusByUsersAccountStatusId(
						complaint.getUsersDetails().getAccountStatus().getUsersAccountStatusId());
		// @formatter:off
		String sendReminderMailBody = 
				  "    <p style='font-size:1.1em'>Hi Admin,</p>"
				+ "    <p>The below complaint is not yet resolved yet:</p>" 
				+ "		Full Name: " + complaint.getUsersDetails().getFirstname() + " " + complaint.getUsersDetails().getLastname() + "</br>" 
				+ "		Email: " + complaint.getUsersDetails().getEmail() + "</br>"
				+ "		Contact no.: " + complaint.getUsersDetails().getMobile() + "</br>" 
				+ "		User's current account status.: " + userAccountStatus.getStatus() + "</br>" 
				+ "		Description: " + complaint.getComplaintDescription() + "</br>" 
				+ "		Type: " + complaint.getComplaintType() + "</br> " 
				+ "		Status: " + complaint.getComplaintStatus() + "</br> " 
				+ "		Created on: " + complaint.getCreatedTime() ;
		// @formatter:on
		MailTemplateModel sendReminderMailTemplate = new MailTemplateModel();
		sendReminderMailTemplate.setUser(complaint.getUsersDetails());
		sendReminderMailTemplate.setMailSubject("Complaint's Reminder of complaint id: " + usersComplaintId);
		sendReminderMailTemplate.setMailBody(sendReminderMailBody);
		if (ConstantMethods.isMailSentSuccessfully(sendReminderMailTemplate)) {
			return ConstantMethods.successRespone(complaint, ConstantVars.COMPLAINT_REMINDER_SENT_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(ConstantVars.COMPLAINT_REMINDER_SENT_FAILED);
		}
	}

}
