package com.cybage.uhs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.model.UsersComplaint;
import com.cybage.uhs.service.implementation.UsersComplaintServiceImpl;

@RestController
@RequestMapping("/complaints")
@CrossOrigin
public class UsersComplaintController {

	private final UsersComplaintServiceImpl usersComplaintService;
	
	@Autowired
	public UsersComplaintController(UsersComplaintServiceImpl usersComplaintService) {
		this.usersComplaintService = usersComplaintService;
	}

	@GetMapping("/get-all")
	public ResponseEntity<ApiResponseEntity> getAllUsers() {
		ApiResponseEntity allComplaints = usersComplaintService.getAllComplaints();
		return new ResponseEntity<>(allComplaints , allComplaints.getHttpStatus());
	}

	@GetMapping("/complaint-by-users-id/{usersId}")
	public ResponseEntity<ApiResponseEntity> getPatientComplaintsIdPatientById(@PathVariable Long usersId) {
		ApiResponseEntity getComplaintsOfUser = usersComplaintService.getComplaintsUsersById(usersId);
		return new ResponseEntity<>(getComplaintsOfUser, getComplaintsOfUser.getHttpStatus());
	}
	
	@GetMapping("/get-complaint-by-id/{patientComplaintsId}")
	public ResponseEntity<ApiResponseEntity> getPatientComplaintsIdById(@PathVariable Long patientComplaintsId) {
		ApiResponseEntity getComplaintResponse = usersComplaintService.getComplaintById(patientComplaintsId);
		return new ResponseEntity<>(getComplaintResponse, getComplaintResponse.getHttpStatus());
	}
	@GetMapping("/send-reminder/{patientComplaintsId}")
	public ResponseEntity<ApiResponseEntity> sendReminder(@PathVariable Long patientComplaintsId) {
		ApiResponseEntity sendReminderRespone= usersComplaintService.sendReminder(patientComplaintsId);
			return new ResponseEntity<>(sendReminderRespone, sendReminderRespone.getHttpStatus());
	}
	
	@PostMapping("/add-complaint")
	public ResponseEntity<ApiResponseEntity> addComplint(@RequestBody UsersComplaint patientComplaints) {
		ApiResponseEntity registerComplaintResonse = usersComplaintService.addComplaint(patientComplaints);
			return new ResponseEntity<>(registerComplaintResonse, registerComplaintResonse.getHttpStatus());
	}
	
	@PostMapping("/update-complaint")
	public ResponseEntity<ApiResponseEntity> updateUser(@RequestBody UsersComplaint complaint) {
		ApiResponseEntity updateComplaintResponse = usersComplaintService.updateComplaint(complaint);
			return new ResponseEntity<>(updateComplaintResponse, updateComplaintResponse.getHttpStatus());
	}
	

	
	
	
	

}
