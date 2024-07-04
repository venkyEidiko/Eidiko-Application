package com.eidiko.dto;

import java.util.List;

import com.eidiko.entity.Address;
//<<<<<<< HEAD
//import com.eidiko.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
//=======


import com.eidiko.entity.Roles_Table;
//>>>>>>> e23a7c1f3f12ad6d4b9c82c7c4c8a7abfc756b48
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class EmployeeDto {


    private Integer id;
    
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNu;
    private Integer employeeId;
    private String gender;
    //my team side navBar response 
    private String location;
    private String jobTitlePrimary;
    private String department;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Roles_Table role;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;
    
    
    

    @JsonIgnore
    public String getPassword() {
    	return this.password;
    }
   
    @JsonIgnore
    public int getId() {
    	return this.id;
    }
}
