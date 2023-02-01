package com.cybage.uhs.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

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
@Entity
public class UsersComplaint {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long usersComplaintId;

	@NotNull
	@OneToOne
	@JoinColumn(name="usersId")
	private Users usersDetails;

	@NotNull
	@Column(name = "complaintStatus")
	private String complaintStatus;

	@NotNull
	@Column(name = "complaintType")
	private String complaintType;

	@NotNull
	@Column(name = "complaintDescription")
	private String complaintDescription;

	
	@Column(name = "adminReply")
	private String adminReply;

	@Column(name = "reminderSentTime")
	private Timestamp reminderSentTime;
	
	@Column(name = "reminderSent")
	private Integer reminderSent;

	@NotNull
	@Column(name = "createdTime")
	private Timestamp createdTime;

}
