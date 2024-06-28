package com.eidiko.entity;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attachmentId;

	private Long empId;
	private String attachmentFileName;
	@Lob
	@Column(name = "fileContent", columnDefinition = "LONGBLOB")
	private byte[] fileContent;
	private String fileExtension;
	@Lob
	@Column(name = "imagedata", columnDefinition = "LONGBLOB")
	private Blob imageData;
//	    @ManyToOne
//	    private EmpLeave empLeave;
}
