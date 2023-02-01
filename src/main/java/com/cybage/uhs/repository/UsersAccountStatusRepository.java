package com.cybage.uhs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybage.uhs.model.UsersAccountStatus;

@Repository
public interface UsersAccountStatusRepository extends JpaRepository<UsersAccountStatus, Integer> {

	UsersAccountStatus findUsersAccountStatusEntityByStatus(String status);
	UsersAccountStatus findUsersAccountStatusIdByStatus(String status);
	UsersAccountStatus findUsersAccountStatusByUsersAccountStatusId(Integer usersAccountStatusId);

}
