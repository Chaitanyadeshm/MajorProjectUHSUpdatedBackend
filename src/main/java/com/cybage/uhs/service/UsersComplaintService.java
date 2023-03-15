package com.cybage.uhs.service;

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.model.UsersComplaint;

public interface UsersComplaintService {

	public ApiResponseEntity getAllComplaints();

	public ApiResponseEntity getComplaintById(Long usersComplaintId);

	public ApiResponseEntity addComplaint(UsersComplaint usersComplaint);

	public ApiResponseEntity updateComplaint(UsersComplaint complaintWithDescription);

	public ApiResponseEntity getComplaintsUsersById(Long usersId);

	public ApiResponseEntity sendReminder(Long usersComplaintId);

}
