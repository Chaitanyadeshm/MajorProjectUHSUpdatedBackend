package com.cybage.uhs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.cybage.uhs.model.PatientsAppointments;
import com.cybage.uhs.service.PatientAppointmentsService;

@RestController
@RequestMapping("/appointments")
@CrossOrigin
public class PatientAppointmentsController {

	@Autowired
	PatientAppointmentsService patientAppointmentsService;

	@GetMapping("/get-all")
	public ResponseEntity<APIResponseEntity> getAllAppointments() {
		APIResponseEntity allAppointments = patientAppointmentsService.getAllAppointments();
		return new ResponseEntity<>(allAppointments, HttpStatus.OK);
	}

	@GetMapping("/get-by-id/{appointmentId}")
	public ResponseEntity<APIResponseEntity> getAppointmentById(@PathVariable Long appointmentId) {
		APIResponseEntity appointment = patientAppointmentsService.getAppointmentById(appointmentId);
		return new ResponseEntity<>(appointment, HttpStatus.OK);
	}

	@GetMapping("/get-by-users-id/{usersId}")
	public ResponseEntity<APIResponseEntity> getAllAppointmentsForDoctor(@PathVariable Long usersId) {
		APIResponseEntity allAppointments = patientAppointmentsService.getAllAppointmentsForUser(usersId);
		return new ResponseEntity<>(allAppointments, HttpStatus.OK);
	}

	@PostMapping("/add-appointment")
	public ResponseEntity<APIResponseEntity> addAppointment(@RequestBody PatientsAppointments patientsAppointments) {
		APIResponseEntity appointmentReponse = patientAppointmentsService.addAppointment(patientsAppointments);
		return new ResponseEntity<>(appointmentReponse, HttpStatus.OK);
	}

	@PutMapping("/update-appointment/{appointmentId}")
	public ResponseEntity<APIResponseEntity> updateAppointment(@RequestBody PatientsAppointments patientsAppointments,
			@PathVariable Long appointmentId) {
		APIResponseEntity updateAppointmentReponse = patientAppointmentsService.updateAppointment(patientsAppointments,
				appointmentId);
		return new ResponseEntity<>(updateAppointmentReponse, HttpStatus.OK);
	}

	
	@PutMapping("/add-prescription/{appointmentId}")
	public ResponseEntity<APIResponseEntity> addAppointmentsPrescription(
			@RequestBody PatientsAppointments patientsAppointments, @PathVariable Long appointmentId) {
		
		APIResponseEntity addAppointmentsPrescriptionReponse = patientAppointmentsService
				.addAppointmentsPrescription(patientsAppointments, appointmentId);
		return new ResponseEntity<>(addAppointmentsPrescriptionReponse, HttpStatus.OK);
	}

	@GetMapping("/change-status/{appointmentId}/{status}")
	public ResponseEntity<APIResponseEntity> changeAppointmentStatus(@PathVariable Long appointmentId,
			@PathVariable String status) {
		APIResponseEntity appointmentStatusResponse = patientAppointmentsService.changeAppointmentStatus(appointmentId,
				status);
		return new ResponseEntity<>(appointmentStatusResponse, HttpStatus.OK);
	}

	@GetMapping("/download-prescription/{appointmentId}")
	public ResponseEntity<Resource> downloadPrescription(@PathVariable Long appointmentId)  {
		
		APIResponseEntity appointment = patientAppointmentsService.getAppointmentById(appointmentId);
		PatientsAppointments appointmentDetails = (PatientsAppointments) appointment.getData();
		
		String prescriptionFileName = appointmentDetails.getPatientDetails().getUsersId() + "_"
				+ appointmentDetails.getAppointmentId() + ".pdf";
		
		File file = new File("E:\\NewEclipseWorkshop\\NewUniversalHealthServices\\src\\main\\resources\\prescription\\"
				+ appointmentDetails.getDoctorDetails().getUsersId() + "\\" + prescriptionFileName);

		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-disposition", "attachment; filename=" + file.getName());

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

}
