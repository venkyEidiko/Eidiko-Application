package com.eidiko.dto;

import com.eidiko.entity.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    
	    @JsonBackReference
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id")
	    private Employee employee;
	    private String doorNumber ;
	    private String streetName;
	    private String landmark;
	    private String area;
	    private String city;
	    private String state;
	    private int pincode;
	    
	   
}
