package com.cybage.uhs.service.implementation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.uhs.bean.ApiResponseEntity;
import com.cybage.uhs.exception.ResourceNotFoundException;
import com.cybage.uhs.model.MailTemplateModel;
import com.cybage.uhs.model.PatientsAppointments;
import com.cybage.uhs.model.Users;
import com.cybage.uhs.repository.PatientApointmentsRepository;
import com.cybage.uhs.repository.UsersRepository;
import com.cybage.uhs.service.PatientAppointmentsService;
import com.cybage.uhs.utils.ConstantMethods;
import com.cybage.uhs.utils.VariablesUtil;

@Service
public class PatientAppointmentsServiceImpl implements PatientAppointmentsService {

	private final PatientApointmentsRepository patientApointmentsRepository;

	private final UsersRepository usersRepository;

	@Autowired
	public PatientAppointmentsServiceImpl(PatientApointmentsRepository patientApointmentsRepository,
			UsersRepository usersRepository) {
		this.patientApointmentsRepository = patientApointmentsRepository;
		this.usersRepository = usersRepository;
	}

	private static Logger logger = Logger.getLogger(PatientAppointmentsServiceImpl.class);

	@Override
	public ApiResponseEntity addAppointment(PatientsAppointments patientsAppointments) {
		BasicConfigurator.configure();
		Users patientDetails = usersRepository
				.findUsersByUsersId(patientsAppointments.getPatientDetails().getUsersId());
		if (patientDetails == null) {
			throw new ResourceNotFoundException(VariablesUtil.INVALID_USER_ID);
		}
		Users doctorDetails = usersRepository.findUsersByUsersId(patientsAppointments.getDoctorDetails().getUsersId());
		patientsAppointments.setPatientDetails(patientDetails);
		patientsAppointments.setDoctorDetails(doctorDetails);
		patientsAppointments.setAppointmentStatus(VariablesUtil.APPONTMENT_STATUS.PENDING.toString());
		patientsAppointments.setCreatedTime(new Timestamp(new Date().getTime()));
		PatientsAppointments newCreatedAppointment = patientApointmentsRepository.save(patientsAppointments);
		if (newCreatedAppointment.getAppointmentId() > 0) {
			logger.info(VariablesUtil.APPOINTMENT_ADDED_SUCCESSFULLY);
			return ConstantMethods.successRespone(newCreatedAppointment, VariablesUtil.APPOINTMENT_ADDED_SUCCESSFULLY);
		} else {
			logger.error(VariablesUtil.APPOINTMENT_ADDED_FAILED);
			return ConstantMethods.failureRespone(VariablesUtil.APPOINTMENT_ADDED_FAILED);
		}
	}

	@Override
	public ApiResponseEntity getAllAppointments() {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date todaysDate = new java.sql.Date(utilDate.getTime());
		return ConstantMethods.successRespone(patientApointmentsRepository.findByAppointmentDateGreaterThanEqual(todaysDate),
				VariablesUtil.ALL_APPOINTMENT_FETCHED_SUCCESSFULLY);
	}

	@Override
	public ApiResponseEntity getAppointmentById(Long appointmentId) {

		return ConstantMethods.successRespone(patientApointmentsRepository.findByAppointmentId(appointmentId),
				VariablesUtil.APPOINTMENT_FOR_ADMIN_FETCHED_SUCCESSFULLY);
	}

	@Override
	public ApiResponseEntity getAllAppointmentsForUser(Long usersId) {
		Users userDetails = usersRepository.findUsersByUsersId(usersId);
		System.out.println("users id: " + usersId);
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		List<PatientsAppointments> usersAppointmentsList = new ArrayList<PatientsAppointments>();
		if (userDetails.getUsersRole().getRoleName().equalsIgnoreCase(VariablesUtil.USERS_ROLE.PATIENT.toString())) {
			usersAppointmentsList = patientApointmentsRepository
					.findByPatientDetailsAndAppointmentDateGreaterThanEqual(userDetails, sqlDate);
		} else {
			usersAppointmentsList = patientApointmentsRepository
					.findByDoctorDetailsAndAppointmentDateGreaterThanEqual(userDetails, sqlDate);
		}
		return ConstantMethods.successRespone(usersAppointmentsList,
				VariablesUtil.APPOINTMENT_FOR_USER_FETCHED_SUCCESSFULLY);
	}

	@Override
	public ApiResponseEntity changeAppointmentStatus(Long appointmentId, String newStatus) {
		PatientsAppointments patientsAppointments = patientApointmentsRepository.findByAppointmentId(appointmentId);
		if (patientsAppointments != null) {
			patientsAppointments.setAppointmentStatus(newStatus);
			PatientsAppointments updatedAppointment = patientApointmentsRepository.save(patientsAppointments);
			return ConstantMethods.successRespone(updatedAppointment,
					VariablesUtil.APPOINTMENT_STATUS_UPDATED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(VariablesUtil.APPOINTMENT_IVALID_ID);
		}
	}

	@Override
	public ApiResponseEntity updateAppointment(PatientsAppointments patientsAppointments, Long appointmentId) {
		patientsAppointments.setAppointmentId(appointmentId);
		PatientsAppointments updatedPatientsAppointments = patientApointmentsRepository.save(patientsAppointments);
		if (updatedPatientsAppointments != null) {
			return ConstantMethods.successRespone(updatedPatientsAppointments,
					VariablesUtil.APPOINTMENT_STATUS_UPDATED_SUCCESSFULLY);
		} else {
			return ConstantMethods.failureRespone(VariablesUtil.APPOINTMENT_IVALID_ID);
		}
	}

	@Override
	public ApiResponseEntity addAppointmentsPrescription(PatientsAppointments patientsAppointments,
			Long appointmentId) {
		PatientsAppointments oldAppointmentDetails = patientApointmentsRepository.findByAppointmentId(appointmentId);
		oldAppointmentDetails.setAppointmentStatus(VariablesUtil.APPONTMENT_STATUS.COMPLETED.toString());
		oldAppointmentDetails.setPrescription(patientsAppointments.getPrescription());
		if (ConstantMethods.isPrescriptionSavedSuccessfully(oldAppointmentDetails,
				oldAppointmentDetails.getDoctorDetails(), oldAppointmentDetails.getPatientDetails())) {
			if (isPrescriptionSentOnMailSuccessfully(oldAppointmentDetails)) {
				PatientsAppointments prescriptionAddedAppointment = patientApointmentsRepository
						.save(oldAppointmentDetails);
				return ConstantMethods.successRespone(prescriptionAddedAppointment,
						VariablesUtil.APPOINTMENT_PRESCRIPTION_UPDATED_SUCCESSFULLY);
			} else {
				return ConstantMethods.failureRespone(VariablesUtil.APPOINTMENT_PRESCRIPTION_UPDATION_FAILED);
			}

		} else {
			return ConstantMethods.failureRespone(VariablesUtil.APPOINTMENT_PRESCRIPTION_UPDATION_FAILED);
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
