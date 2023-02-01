package com.cybage.uhs.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UsersAccountStatus {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer usersAccountStatusId;

	@Column(name = "status")
	private String status;	
	
	@OneToMany(mappedBy = "accountStatus")
	@JsonIgnore
	private List<Users> users;
	
	
	
	
	
	
}
