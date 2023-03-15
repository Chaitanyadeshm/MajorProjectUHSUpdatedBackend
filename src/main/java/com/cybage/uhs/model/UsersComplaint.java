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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Details about the user's complaint")
public class UsersComplaint {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@ApiModelProperty(notes = "The unique id of the user's complaint")
	private Long usersComplaintId;

	@NotNull
	@OneToOne
	@JoinColumn(name="usersId")
	@ApiModelProperty(notes = "The user details associated with the complaint")
	private Users usersDetails;

	@Column(name = "complaintStatus")
	@ApiModelProperty(notes = "The status of the complaint")
	private String complaintStatus;

	@NotNull
	@Column(name = "complaintType")
	@ApiModelProperty(notes = "The type of the complaint")
	private String complaintType;

	@NotNull
	@Column(name = "complaintDescription")
	@ApiModelProperty(notes = "The description of the complaint")
	private String complaintDescription;
	
	@Column(name = "adminReply")
	@ApiModelProperty(notes = "The reply given by the admin for the complaint")
	private String adminReply;

	@Column(name = "reminderSentTime")
	@ApiModelProperty(notes = "The time at which the reminder was sent")
	private Timestamp reminderSentTime;
	
	@Column(name = "reminderSent")
	@ApiModelProperty(notes = "The number of reminders sent")
	private Integer reminderSent;

	@Column(name = "createdTime")
	@ApiModelProperty(notes = "The time at which the complaint was created")
	private Timestamp createdTime;

}