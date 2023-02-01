package com.cybage.uhs.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybage.uhs.model.PatientsAppointments;
import com.cybage.uhs.model.Users;

@Repository
public interface PatientApointmentsRepository extends JpaRepository<PatientsAppointments, Integer> {

	PatientsAppointments findByAppointmentId(Long appointmentId);
	
	List<PatientsAppointments> findAllByOrderByAppointmentIdDesc();
	
	List<PatientsAppointments> findByPatientDetails(Users patientDetails);
	
	List<PatientsAppointments> findByDoctorDetails(Users doctorDetails);

}
