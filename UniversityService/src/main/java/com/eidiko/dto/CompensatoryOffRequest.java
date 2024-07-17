package com.eidiko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompensatoryOffRequest {

    private String fromDate;
    private String toDate;
    private String note;
    private List<MultipartFile> files;
}