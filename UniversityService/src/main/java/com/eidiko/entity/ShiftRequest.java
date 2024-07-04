package com.eidiko.entity;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shiftId;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String status;
	private String newShift;
	private String reason;
	@ElementCollection
	private Set<String> notifyTo;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "employeeId")
	    @JsonBackReference
    private Employee employee;  // Link to Employee
}
