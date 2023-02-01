package com.cybage.uhs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybage.uhs.model.FilesModel;

@Repository
public interface FilesRepository extends JpaRepository<FilesModel, Integer> {

	FilesModel findFilesModelByFilesModelId(Long filesModelId);
	

}
