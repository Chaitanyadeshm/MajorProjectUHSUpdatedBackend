package com.cybage.uhs.service;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.model.UsersComplaint;

public interface UsersComplaintService {

	public APIResponseEntity getAllComplaints();

	public APIResponseEntity getComplaintById(Long usersComplaintId);

	public APIResponseEntity addComplaint(UsersComplaint usersComplaint);

	public APIResponseEntity updateComplaint(UsersComplaint complaint, Long usersComplaintId);

	public APIResponseEntity getComplaintsUsersById(Long usersId);

	public APIResponseEntity sendReminder(Long usersComplaintId);

}
