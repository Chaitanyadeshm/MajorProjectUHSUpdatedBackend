package com.cybage.uhs.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Details about the user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "The unique id of the user")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long usersId;

	@ApiModelProperty(notes = "The first name of the user")
	@NotNull
	@Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){1,24}$", message = "Firstname should contains atleast 2 characters")
	@Column
	private String firstname;

	@ApiModelProperty(notes = "The last name of the user")
	@NotNull
	@Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){1,24}$", message = "Lastname should contains atleast 2 characters")
	@Column
	private String lastname;

	@ApiModelProperty(notes = "The email id of the user")
	@NotNull
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message="Invalid Email id")
	@Column
	private String email;

	@ApiModelProperty(notes = "The profile url of the user")
	@Column
	private String usersProfileUrl;

	@ApiModelProperty(notes = "The mobile number of the user")
	@NotNull
//	@Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
	@Pattern(regexp = "(^$|[1-9]{10})", message="Mobile number should be 10 digits and it cannot start with 0")
	@Column
	private String mobile;

	@ApiModelProperty(notes = "The number of login attempts made by the user")
	@Column
	private Integer loginAttempts;

	@ApiModelProperty(notes = "The OTP sent to the user for authentication")
	@Column
	private Integer usersOTP;

	@ApiModelProperty(notes = "The timestamp when the user account was created")
//	@NotNull
	@Column
	private Timestamp accountCreated;

	@ApiModelProperty(notes = "The role assigned to the user")
//	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JsonIgnoreProperties(value = { "Users", "hibernateLazyInitializer" })
	@JoinColumn(name = "usersRoleId")
	private UsersRole usersRole;

	@ApiModelProperty(notes = "The account status of the user")
//	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JsonIgnoreProperties(value = { "Users", "hibernateLazyInitializer" })
	@JoinColumn(name = "usersAccountStatus")
	private UsersAccountStatus accountStatus;

	@ApiModelProperty(notes = "The specializations of the user")
	// @formatter:off
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "doctors_specializations", 
        joinColumns = {
            @JoinColumn(name = "usersId") },
        inverseJoinColumns = {
            @JoinColumn(name = "specializationId") }
    )
    private Set<Specialization> specialization = new HashSet<>();
    // @formatter:on

	public void addSpecializataion(Specialization newSpecialization) {
		this.specialization.add(newSpecialization);
		newSpecialization.getUsers().add(this);
	}

}
