package com.eidiko.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkFromHomeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeName;
    private Long employeeID;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String fromHalf;
    private LocalDate applyDate=LocalDate.now();
    private String toHalf;
    private String reason;
    @Column(columnDefinition = "LONGBLOB")
    private String notify;
    private String status = "Pending"; // Default status to PENDING
    private String requestType;
    private String rejectReason;
    private String actionTakenBy;
    public double getRequestedDays() {
        double days = fromDate.until(toDate).getDays() + 1; // Calculate total days inclusive
        if ("Second Half".equals(fromHalf)) days -= 0.5;
        if ("First Half".equals(toHalf)) days -= 0.5;
        return days;
    }
}