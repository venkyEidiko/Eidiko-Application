package com.eidiko.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
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
	@ElementCollection
	private Set<Long> notifyTo;
	private String actionTakenBy;
	private String leaveNote;
	private String rejectionReason;
	private Long employeeId;
	private String customDayStatus;
//	@OneToMany(mappedBy = "empLeave", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    private List<Attachment> leaveAttachment; 
	private double durationInDays;
	
}
