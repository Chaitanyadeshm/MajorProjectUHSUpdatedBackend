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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usersId")
	private Long usersId;

	@NotNull
	@Column(name = "firstname")
	private String firstname;

	@NotNull
	@Column(name = "lastname")
	private String lastname;

	@NotNull
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	@Column(name = "email")
	private String email;

	@Column(name = "usersProfileUrl")
	private String usersProfileUrl;

	@NotNull
	@Size(min = 10, max = 10)
	@Pattern(regexp = "(^$|[0-9]{10})")
	@Column(name = "mobile")
	private String mobile;

	@Column(name = "loginAttempts")
	private Integer loginAttempts;

	@Column(name = "usersOTP")
	private Integer usersOTP;

	@NotNull
	@Column(name = "accountCreated")
	private Timestamp accountCreated;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JsonIgnoreProperties(value = { "Users", "hibernateLazyInitializer" })
	@JoinColumn(name = "usersRoleId")
	private UsersRole usersRole;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JsonIgnoreProperties(value = { "Users", "hibernateLazyInitializer" })
	@JoinColumn(name = "usersAccountStatus")
	private UsersAccountStatus accountStatus;

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
