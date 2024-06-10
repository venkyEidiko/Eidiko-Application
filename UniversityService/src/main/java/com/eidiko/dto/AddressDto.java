package com.eidiko.dto;

import com.eidiko.entity.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data
public class AddressDto {

	
	  private Long id;
	    
	    @JsonBackReference
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id")
	    private Employee employee;
	    
	    private String addressLine2;
	    private String city;
	    private String state;
}
