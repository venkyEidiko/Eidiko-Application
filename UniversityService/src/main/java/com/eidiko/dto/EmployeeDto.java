package com.eidiko.dto;

import java.util.List;

import com.eidiko.entity.Address;

import com.eidiko.entity.Roles;
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
    
    private Integer employeeId;
    private String gender;
    @ManyToOne(fetch = FetchType.EAGER)
    private Roles role;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;
}
