package com.cybage.uhs.service;

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.model.PatientsAppointments;

public interface PatientAppointmentsService {


	public ApiResponseEntity addAppointment(PatientsAppointments patientsAppointments);

	public ApiResponseEntity getAllAppointments();

	public ApiResponseEntity getAppointmentById(Long appointmentId);

	public ApiResponseEntity getAllAppointmentsForUser(Long usersId);

	public ApiResponseEntity changeAppointmentStatus(Long appointmentId, String newStatus);

	public ApiResponseEntity updateAppointment(PatientsAppointments patientsAppointments, Long appointmentId);

	public ApiResponseEntity addAppointmentsPrescription(PatientsAppointments patientsAppointments,
			Long appointmentId);
	
}
