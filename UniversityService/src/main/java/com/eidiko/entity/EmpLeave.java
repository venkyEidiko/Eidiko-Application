package com.eidiko.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EmpLeave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long leaveId;
	private LocalDate leaveDates;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String status;
	private String leaveType;
	private String requestedBy;
	private Set<String> notifyTo;
	private String actionTakenBy;
	private String leaveNote;
	private String rejectionReason;
	private Long employeeId;
	private String customDayStatus;
//	@OneToMany(mappedBy = "empLeave", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    private List<Attachment> leaveAttachment; 
	private double durationInDays;
	
}
