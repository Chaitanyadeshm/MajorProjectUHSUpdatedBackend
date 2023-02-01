package com.cybage.uhs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybage.uhs.model.UsersRole;

@Repository
public interface UsersRoleRepository extends JpaRepository<UsersRole, Long> {

	UsersRole findUsersRoleByUsersRoleId(Long usersRoleId);

	UsersRole findUsersRoleByRoleNameIgnoreCase(String roleName);
	

	
}
