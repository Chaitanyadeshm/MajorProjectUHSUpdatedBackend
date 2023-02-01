package com.cybage.uhs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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
public class FilesModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(name = "filesModelId")
    private Long filesModelId;
    private String fileName;

    private String fileType;

    @Lob
    private byte[] prescription;
    @Lob
    private byte[] feedback;
	public FilesModel(String fileName, String fileType, byte[] prescription, byte[] feedback) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.prescription = prescription;
		this.feedback = feedback;
	}

}
