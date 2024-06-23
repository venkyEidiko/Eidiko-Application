package com.eidiko.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private Long employeeID;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String fromHalf;
    private String toHalf;
    private String reason;
    private String notify;
    private String status = "PENDING"; // Default status to PENDING
    private String requestType;

    public double getRequestedDays() {
        double days = fromDate.until(toDate).getDays() + 1; // Calculate total days inclusive
        if ("Second Half".equals(fromHalf)) days -= 0.5;
        if ("First Half".equals(toHalf)) days -= 0.5;
        return days;
    }
}