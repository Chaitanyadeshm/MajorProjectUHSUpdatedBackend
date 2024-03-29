package com.cybage.uhs.service.implementation;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.model.MailTemplateModel;
import com.cybage.uhs.model.Users;
import com.cybage.uhs.model.UsersAccountStatus;
import com.cybage.uhs.model.UsersComplaint;
import com.cybage.uhs.repository.UsersAccountStatusRepository;
import com.cybage.uhs.repository.UsersComplaintRepository;
import com.cybage.uhs.repository.UsersRepository;
import com.cybage.uhs.service.UsersComplaintService;
import com.cybage.uhs.utils.ConstantMethods;
import com.cybage.uhs.utils.VariablesUtil;

@Service
public class UsersComplaintServiceImpl implements UsersComplaintService {
	private final UsersRepository usersRepository;
	private final UsersComplaintRepository usersComplaintRepository;
	private final UsersAccountStatusRepository usersAccountStatusRepository;

	@Autowired
	public UsersComplaintServiceImpl(UsersRepository usersRepository, UsersComplaintRepository usersComplaintRepository,
			UsersAccountStatusRepository usersAccountStatusRepository) {
		super();
		this.usersRepository = usersRepository;
		this.usersComplaintRepository = usersComplaintRepository;
		this.usersAccountStatusRepository = usersAccountStatusRepository;
	}

	@Override
	public ApiResponseEntity getAllComplaints() {
		List<UsersComplaint> allComplaints = usersComplaintRepository.findAll();
		return ConstantMethods.successRespone(allComplaints, "All complaints fetched successfully.");
	}

	@Override
	public ApiResponseEntity getComplaintById(Long usersComplaintId) {
		UsersComplaint complaint = usersComplaintRepository.findByUsersComplaintId(usersComplaintId);
		return ConstantMethods.successRespone(complaint,
				"Complaint with id " + usersComplaintId + " fetched successfully.");
	}

	@Override
	public ApiResponseEntity addComplaint(UsersComplaint usersComplaint) {
		usersComplaint.setComplaintStatus(VariablesUtil.COMPLAINT_STATUS.UNRESOLVED.toString());
		usersComplaint.setCreatedTime(new Timestamp(new Date().getTime()));
		usersComplaint.setReminderSent(0);
		Users usersDetails = usersRepository.findUsersByUsersId(usersComplaint.getUsersDetails().getUsersId());
		usersComplaint.setUsersDetails(usersDetails);
		UsersComplaint newPatientComplaints = usersComplaintRepository.save(usersComplaint);
		return sendComplaintDetailsOnMail(newPatientComplaints);
	}

	private ApiResponseEntity sendComplaintDetailsOnMail(UsersComplaint complaint) {
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
			return ConstantMethods.successRespone(complaint, VariablesUtil.COMPLAINT_REGISTERED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(VariablesUtil.COMPLAINT_REGISTERATION_FAILED);
		}

	}

	@Override
	public ApiResponseEntity updateComplaint(UsersComplaint complaintWithDescription) {
		System.out.println("complaint id: "+ complaintWithDescription.getUsersComplaintId());
		UsersComplaint complaint = usersComplaintRepository.findByUsersComplaintId(complaintWithDescription.getUsersComplaintId());
		System.out.println("status: "+ VariablesUtil.COMPLAINT_STATUS.RESOLVED.toString());
		complaint.setComplaintStatus(VariablesUtil.COMPLAINT_STATUS.RESOLVED.toString());
		System.out.println("complaint's status: "+ complaint.getComplaintStatus());
		complaint.setAdminReply(complaintWithDescription.getAdminReply());
		UsersComplaint updatedComplaint = usersComplaintRepository.save(complaint);
		return sendComplaintUpdatesOnMail(updatedComplaint);
	}

	private ApiResponseEntity sendComplaintUpdatesOnMail(UsersComplaint complaint) {
		//@formatter:off
		String complaintUpdatesMailBody = 
				  "    <p style='font-size:1.1em'>Hi, " + complaint.getUsersDetails().getFirstname() + " " + complaint.getUsersDetails().getLastname() +",</p>" 
				+ "    <p> Your complaint has been resolved. "
				+ " 	We are sorry for the inconvenience.</p>"
				+ "    <p> Please find below your complaint's details:</p>" 
				+ "		Description: " + complaint.getComplaintDescription() + "</br>" 
				+ "		Type: " + complaint.getComplaintType() + "</br> " 
				+ "		Updated Status: " + complaint.getComplaintStatus() + "</br> " 
				+ "		Response: " + complaint.getAdminReply() + "</br> " 
				+ "		Created on: " + complaint.getCreatedTime();
		//@formatter:on
		MailTemplateModel updatesOnComplaintMailTemplate = new MailTemplateModel();
		updatesOnComplaintMailTemplate.setMailSubject("Updates on your Complaint");
		updatesOnComplaintMailTemplate.setUser(complaint.getUsersDetails());
		updatesOnComplaintMailTemplate.setMailBody(complaintUpdatesMailBody);
		if (ConstantMethods.isMailSentSuccessfully(updatesOnComplaintMailTemplate)) {
			return ConstantMethods.successRespone(complaint, VariablesUtil.COMPLAINT_UPDATED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(VariablesUtil.COMPLAINT_UPDATION_FAILED);
		}
	}

	@Override
	public ApiResponseEntity getComplaintsUsersById(Long usersId) {
		Users userDetails = usersRepository.findUsersByUsersId(usersId);
		List<UsersComplaint> allComplaintsPatientId = usersComplaintRepository.findByUsersDetails(userDetails);
		return ConstantMethods.successRespone(allComplaintsPatientId, "All complaints for user fetched successfully.");
	}

	@Override
	public ApiResponseEntity sendReminder(Long usersComplaintId) {
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
			return ConstantMethods.successRespone(complaint, VariablesUtil.COMPLAINT_REMINDER_SENT_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(VariablesUtil.COMPLAINT_REMINDER_SENT_FAILED);
		}
	}

}
