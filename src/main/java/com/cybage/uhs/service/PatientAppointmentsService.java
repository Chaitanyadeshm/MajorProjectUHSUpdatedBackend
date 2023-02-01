package com.cybage.uhs.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.uhs.bean.APIResponseEntity;
import com.cybage.uhs.exception.ResourceNotFoundException;
import com.cybage.uhs.model.MailTemplateModel;
import com.cybage.uhs.model.PatientsAppointments;
import com.cybage.uhs.model.Users;
import com.cybage.uhs.repository.PatientApointmentsRepository;
import com.cybage.uhs.repository.UsersRepository;
import com.cybage.uhs.utils.ConstantMethods;
import com.cybage.uhs.utils.ConstantVars;

@Service
public class PatientAppointmentsService {

	@Autowired
	private PatientApointmentsRepository patientApointmentsRepository;

	@Autowired
	private UsersRepository usersRepository;

	private static Logger logger = Logger.getLogger(PatientAppointmentsService.class);

	public APIResponseEntity addAppointment(PatientsAppointments patientsAppointments) {
		BasicConfigurator.configure();
		Users patientDetails = usersRepository
				.findUsersByUsersId(patientsAppointments.getPatientDetails().getUsersId());
		if(patientDetails == null) {
			throw new ResourceNotFoundException(ConstantVars.INVALID_USER_ID);
		}
		Users doctorDetails = usersRepository.findUsersByUsersId(patientsAppointments.getDoctorDetails().getUsersId());
		patientsAppointments.setPatientDetails(patientDetails);
		patientsAppointments.setDoctorDetails(doctorDetails);
		patientsAppointments.setAppointmentStatus(ConstantVars.APPONTMENT_STATUS.PENDING.toString());
		patientsAppointments.setCreatedTime(new Timestamp(new Date().getTime()));
		PatientsAppointments newCreatedAppointment = patientApointmentsRepository.save(patientsAppointments);
		if (newCreatedAppointment.getAppointmentId() > 0) {
			logger.info(ConstantVars.APPOINTMENT_ADDED_SUCCESSFULLY);
			return ConstantMethods.successRespone(newCreatedAppointment, ConstantVars.APPOINTMENT_ADDED_SUCCESSFULLY);
		} else {
			logger.error(ConstantVars.APPOINTMENT_ADDED_FAILED);
			return ConstantMethods.failureRespone(ConstantVars.APPOINTMENT_ADDED_FAILED);
		}
	}

	public APIResponseEntity getAllAppointments() {
		return ConstantMethods.successRespone(patientApointmentsRepository.findAll(),
				ConstantVars.ALL_APPOINTMENT_FETCHED_SUCCESSFULLY);
	}

	public APIResponseEntity getAppointmentById(Long appointmentId) {
		return ConstantMethods.successRespone(
				patientApointmentsRepository.findByAppointmentId(appointmentId),
				ConstantVars.APPOINTMENT_FOR_ADMIN_FETCHED_SUCCESSFULLY);
	}

	public APIResponseEntity getAllAppointmentsForUser(Long usersId) {
		Users userDetails = usersRepository.findUsersByUsersId(usersId);
		
		List<PatientsAppointments> usersAppointmentsList = patientApointmentsRepository
				.findByDoctorDetails(userDetails);
		return ConstantMethods.successRespone(usersAppointmentsList,
				ConstantVars.APPOINTMENT_FOR_USER_FETCHED_SUCCESSFULLY);
	}


	public APIResponseEntity changeAppointmentStatus(Long appointmentId, String newStatus) {
		PatientsAppointments patientsAppointments = patientApointmentsRepository
				.findByAppointmentId(appointmentId);
		if (patientsAppointments != null) {
			patientsAppointments.setAppointmentStatus(newStatus);
			PatientsAppointments updatedAppointment = patientApointmentsRepository.save(patientsAppointments);
			return ConstantMethods.successRespone(updatedAppointment,
					ConstantVars.APPOINTMENT_STATUS_UPDATED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(ConstantVars.APPOINTMENT_IVALID_ID);
		}
	}

	public APIResponseEntity updateAppointment(PatientsAppointments patientsAppointments, Long appointmentId) {
		patientsAppointments.setAppointmentId(appointmentId);
		PatientsAppointments updatedPatientsAppointments = patientApointmentsRepository.save(patientsAppointments);
		if (updatedPatientsAppointments != null) {
			return ConstantMethods.successRespone(updatedPatientsAppointments,
					ConstantVars.APPOINTMENT_STATUS_UPDATED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(ConstantVars.APPOINTMENT_IVALID_ID);
		}
	}

	public APIResponseEntity addAppointmentsPrescription(PatientsAppointments patientsAppointments,
			Long appointmentId) {
		PatientsAppointments oldAppointmentDetails = patientApointmentsRepository.findByAppointmentId(appointmentId);
		oldAppointmentDetails.setAppointmentStatus(ConstantVars.APPONTMENT_STATUS.COMPLETED.toString());
		oldAppointmentDetails.setPrescription(patientsAppointments.getPrescription());
		if(ConstantMethods.isPrescriptionSavedSuccessfully(oldAppointmentDetails, oldAppointmentDetails.getDoctorDetails(), oldAppointmentDetails.getPatientDetails())) {
				if(isPrescriptionSentOnMailSuccessfully(oldAppointmentDetails)) {
					PatientsAppointments prescriptionAddedAppointment = patientApointmentsRepository.save(oldAppointmentDetails);
					return ConstantMethods.successRespone(prescriptionAddedAppointment,
							ConstantVars.APPOINTMENT_PRESCRIPTION_UPDATED_SUCCESSFULLY);
				}else {
					return ConstantMethods.failureRespone(ConstantVars.APPOINTMENT_PRESCRIPTION_UPDATION_FAILED);
				}
				
		}else {
			return ConstantMethods.failureRespone(ConstantVars.APPOINTMENT_PRESCRIPTION_UPDATION_FAILED);
		}
		
	}

	private boolean isPrescriptionSentOnMailSuccessfully(PatientsAppointments appointment) {
		MailTemplateModel prescriptionMailTemplate = new MailTemplateModel();
		prescriptionMailTemplate.setMailSubject("Updates on Appointment");
		prescriptionMailTemplate.setUser(appointment.getPatientDetails());

// @formatter:off
		String prescriptionMailBody ="    <p >Your appointment has been completed.  </p>"
				+ "    <p style='font-size:1.1em'> Please find below the details of your appointment:</p>"
				+ "		Doctor name: " + appointment.getDoctorDetails().getFirstname() + " " + appointment.getDoctorDetails().getLastname() + "</br>" 
				+ "		Appointment date: " + appointment.getAppointmentDate() + "</br> "
				+ "		Appointment time: " + appointment.getAppointmentTime() + "</br> "
				+ "		Appointment Status: " + appointment.getAppointmentStatus() + "</br> "
				+ "		Appointment Created on: " + appointment.getCreatedTime()  + "</br>"
				+ "     Prescription: " + appointment.getPrescription() ;
 
// @formatter:on

		prescriptionMailTemplate.setMailBody(prescriptionMailBody);
		return ConstantMethods.isMailSentSuccessfully(prescriptionMailTemplate);

	}
	


}
