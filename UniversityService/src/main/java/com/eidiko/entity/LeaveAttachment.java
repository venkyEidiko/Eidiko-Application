package com.eidiko.entity;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class LeaveAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 
	private String attachmentFileName;
    private byte[] fileContent;
    private String fileExtension;
    @Lob
    @Column(name = "imagedata",columnDefinition = "BLOB")
    private Blob imageData;
	@ManyToOne
	private EmpLeave empLeave;
}

