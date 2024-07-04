package com.eidiko.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompensatoryRequest {

	public LocalDate fromDate;
	public LocalDate toDate;
	public String note;
	public List<MultipartFile>files;
	public Long employeeId;
}
