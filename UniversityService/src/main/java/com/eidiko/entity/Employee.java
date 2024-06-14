package com.eidiko.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Employee_DETAILS_TABLE")
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements UserDetails{


//	 @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private Integer id;
//	    
//	    private String firstName;
//	    private String lastName;
//	    private String password;
//	    private String email;
//	    
//	    private Integer employeeId;
//	    private String gender;
//	    @Enumerated(EnumType.STRING)
//	    private Role role;
//	    
//	    @JsonManagedReference
//	    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	    private List<Address> addresses;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String phoneNu;
	private Integer employeeId;
	private String gender;
	@ManyToOne(fetch = FetchType.EAGER)
	private Roles role;

	@JsonManagedReference
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Address> addresses;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}

	@Override
	public String getUsername() {
	
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
