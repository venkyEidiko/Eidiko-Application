package com.eidiko.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

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
    private byte[] fileContent;
    private String fileExtension;
    @Lob
    @Column(name = "imagedata",columnDefinition = "BLOB")
    private Blob imageData;
}
