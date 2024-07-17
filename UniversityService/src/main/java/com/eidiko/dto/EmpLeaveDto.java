package com.eidiko.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.eidiko.entity.Attachment;

import lombok.Data;

@Data
public class EmpLeaveDto {

	private long leaveId;
	private LocalDate leaveDates;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String status;
	private String leaveType;
	private String requestedBy;
	private Set<Long> notifyTo;
	private String actionTakenBy;
	private String leaveNote;
	private String rejectionReason;
	private Long employeeId;
	private String customDayStatus;
	private List<Attachment> leaveAttachment; 
	private double durationInDays; 
}
