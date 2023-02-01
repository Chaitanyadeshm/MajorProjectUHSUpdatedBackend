package com.cybage.uhs.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybage.uhs.model.UsersComplaint;
import com.cybage.uhs.model.Users;

@Repository
public interface UsersComplaintRepository extends JpaRepository<UsersComplaint, Integer> {
	UsersComplaint findByUsersComplaintId(Long patientComplaintsId);
	
	List<UsersComplaint> findByUsersDetails(Users userDetails);
	
}
