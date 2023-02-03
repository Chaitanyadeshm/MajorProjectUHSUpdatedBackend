package com.cybage.uhs.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybage.uhs.model.Specialization;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

	List<Specialization> findSpecializationBySpecializationIdIn(List<Integer> specializationId);
	
	Specialization findSpecializatinBySpecializationId(Long specializationId);
	
//	List<Specialization>

}
