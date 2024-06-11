package com.eidiko.dto;

import java.util.List;

import com.eidiko.entity.Address;
import com.eidiko.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class EmployeeDto {

	
	  private String firstName;
	    private String lastName;
	    private String password;
	    private String email;
	    
	    private Integer employeeId;
	    private String gender;
	    @Enumerated(EnumType.STRING)
	    private Role role;
	    
	    @JsonManagedReference
	    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private List<Address> addresses;
	    
	    @JsonIgnore
	    public String getPassword() {
	    	return this.password;
	    }
	    
	    
}
