package com.cybage.uhs.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class PatientsAppointments implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appointment_id")
	private Long appointmentId;

	@NotNull(message = "Appointment date cannot be null")
	@Column(name = "appointmentDate")
	private Date appointmentDate;

	@NotNull(message = "Appointment time cannot be null")
	@Column(name = "appointmentTime")
	private Time appointmentTime;

	@NotNull
	@Column(name = "appointmentStatus")
	private String appointmentStatus;

	@NotNull
	@OneToOne
	@JoinColumn(name = "doctorId")
	private Users doctorDetails;

	@NotNull
	@OneToOne
	@JoinColumn(name = "patientId")
	private Users patientDetails;

	@Column(name = "patientsNotes")
	private String patientsNotes;

	@Column(name = "prescription")
	private String prescription;

	@Column(name = "feedback")
	private String feedback;

	@Column(name = "doctorsRatings")
	private Integer doctorsRatings;

	@NotNull
	@Column(name = "created_time")
	private Timestamp createdTime;

	public PatientsAppointments(Long appointmentId, @NotNull Date appointmentDate, @NotNull Time appointmentTime,
			@NotNull String appointmentStatus, @NotNull Users patientDetails, String patientsNotes, String prescription,
			String feedback, Integer doctorsRatings, @NotNull Timestamp createdTime) {
		super();
		this.appointmentId = appointmentId;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.appointmentStatus = appointmentStatus;
		this.patientDetails = patientDetails;
		this.patientsNotes = patientsNotes;
		this.prescription = prescription;
		this.feedback = feedback;
		this.doctorsRatings = doctorsRatings;
		this.createdTime = createdTime;
	}

	
	
	
}
