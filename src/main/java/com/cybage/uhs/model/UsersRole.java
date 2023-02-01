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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@Entity
public class UsersRole {
		
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usersRoleId;

	@Column
	private String roleName;	
	
	@OneToMany(mappedBy= "usersRole")
	@JsonIgnore
	private List<Users> users;

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

