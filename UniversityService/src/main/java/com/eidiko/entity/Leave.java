package com.eidiko.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "leave_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long leaveId;

    private String leaveDates;
    private String fromDate;
    private String toDate;
    private String status;
    private String leaveType;
    private String requestedBy;

    @ElementCollection
    private Set<String> notifyTo;

    private String actionTakenBy;
    private String leaveNote;
    private String rejectionReason;
    private Long employeeId;

}
