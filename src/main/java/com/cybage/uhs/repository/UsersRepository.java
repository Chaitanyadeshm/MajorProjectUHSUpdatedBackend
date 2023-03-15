package com.cybage.uhs.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybage.uhs.model.Specialization;
import com.cybage.uhs.model.Users;
import com.cybage.uhs.model.UsersAccountStatus;
import com.cybage.uhs.model.UsersRole;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	List<Users> findAllByOrderByUsersIdDesc();
	
	List<Users> findUsersByUsersRole(UsersRole usersRole);
	
	List<Users> findUsersByAccountStatus(UsersAccountStatus usersAccountStatus);
	
	List<Users> findUsersByAccountStatusNotLike(UsersAccountStatus usersAccountStatus);
	
	Users findUsersByUsersId(Long usersId);
	
	Users findUsersByEmail(String email);
	
	List<Users> findUsersByUsersRoleAndSpecializationIn(UsersRole usersRole, List<Specialization> specializations );
	
	boolean existsByEmail(String email);
	
	
}
