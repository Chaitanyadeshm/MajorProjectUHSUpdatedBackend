package com.cybage.uhs.service;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.model.PatientsAppointments;

public interface PatientAppointmentsService {


	public APIResponseEntity addAppointment(PatientsAppointments patientsAppointments);

	public APIResponseEntity getAllAppointments();

	public APIResponseEntity getAppointmentById(Long appointmentId);

	public APIResponseEntity getAllAppointmentsForUser(Long usersId);

	public APIResponseEntity changeAppointmentStatus(Long appointmentId, String newStatus);

	public APIResponseEntity updateAppointment(PatientsAppointments patientsAppointments, Long appointmentId);

	public APIResponseEntity addAppointmentsPrescription(PatientsAppointments patientsAppointments,
			Long appointmentId);
	


}
