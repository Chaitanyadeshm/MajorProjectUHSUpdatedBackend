package com.cybage.uhs.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
//@Data // @ToString @Getter @Setter @RequiredArgsConstructor
@Getter
@Setter
@Entity
@ApiModel(description = "Class representing a user role in the system.")
public class UsersRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The unique ID of the user role.")
	private Long usersRoleId;

	@Column
	@ApiModelProperty(notes = "The name of the user role.")
	private String roleName;

	@OneToMany(mappedBy = "usersRole")
	@JsonIgnore
	private List<Users> users;

}