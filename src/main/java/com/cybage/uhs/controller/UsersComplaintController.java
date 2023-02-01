package com.cybage.uhs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.model.UsersComplaint;
import com.cybage.uhs.service.UsersComplaintService;

@RestController
@RequestMapping("/patients-complaints")
@CrossOrigin
public class UsersComplaintController {

	@Autowired
	private UsersComplaintService usersComplaintService;
	
	@GetMapping("/get-all")
	public ResponseEntity<APIResponseEntity> getAllUsers() {
		APIResponseEntity allComplaints = usersComplaintService.getAllComplaints();
		return new ResponseEntity<>(allComplaints , allComplaints.getHttpStatus());
	}

	@GetMapping("/complaint-by-users-id/{usersId}")
	public ResponseEntity<APIResponseEntity> getPatientComplaintsIdPatientById(@PathVariable Long usersId) {
		APIResponseEntity getComplaintsOfUser = usersComplaintService.getComplaintsUsersById(usersId);
		return new ResponseEntity<>(getComplaintsOfUser, getComplaintsOfUser.getHttpStatus());
	}
	
	@GetMapping("/get-complaint-by-id/{patientComplaintsId}")
	public ResponseEntity<APIResponseEntity> getPatientComplaintsIdById(@PathVariable Long patientComplaintsId) {
		APIResponseEntity getComplaintResponse = usersComplaintService.getComplaintById(patientComplaintsId);
		return new ResponseEntity<>(getComplaintResponse, getComplaintResponse.getHttpStatus());
	}
	@GetMapping("/send-reminder/{patientComplaintsId}")
	public ResponseEntity<APIResponseEntity> sendReminder(@PathVariable Long patientComplaintsId) {
		APIResponseEntity sendReminderRespone= usersComplaintService.sendReminder(patientComplaintsId);
			return new ResponseEntity<>(sendReminderRespone, sendReminderRespone.getHttpStatus());
	}
	
	@PostMapping("/add-complaint")
	public ResponseEntity<APIResponseEntity> addComplint(@RequestBody UsersComplaint patientComplaints) {
		APIResponseEntity registerComplaintResonse = usersComplaintService.addComplaint(patientComplaints);
			return new ResponseEntity<>(registerComplaintResonse, registerComplaintResonse.getHttpStatus());
	}
	
	@PutMapping("/update-complaint/{patientComplaintsId}")
	public ResponseEntity<APIResponseEntity> updateUser(@RequestBody UsersComplaint complaint, @PathVariable Long patientComplaintsId) {
		APIResponseEntity updateComplaintResponse = usersComplaintService.updateComplaint(complaint, patientComplaintsId);
			return new ResponseEntity<>(updateComplaintResponse, updateComplaintResponse.getHttpStatus());
	}
	

	
	
	
	

}
