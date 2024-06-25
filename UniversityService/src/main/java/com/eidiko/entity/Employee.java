package com.eidiko.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@Data
@Table(name = "Employee_DETAILS_TABLE")
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	private Long employeeId;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String phoneNu;
	private String gender;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Roles_Table role;

	@ToString.Exclude
	@JsonManagedReference
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Address> addresses;

	//contact details
	private String workEmail;
    private String personalEmail;
    private String workNumber;
    private String residenceNumber;
    private String skype;
    private String emergencyContactNumber;
	
	//primary details
	private LocalDate dateOfBirth;
	private String maritalStatus;
	private String bloodGroup;
	private String physicallyHandicapped;
	private String nationality;

	//Job Details
	private LocalDate dateOfJoining;
	private String jobTitlePrimary;
	private String jobTitleSecondry;
	private String workerType;
	private String timeType;
	private String noticePeriod;
	private String payBand;
	private String payGrade;
	private String contractStatus;
	private String inProbation;

	//Employee Time
	private String shift;
    private String weeklyOffPolicy;
    private String leavePlan;
    private String holidayCalendar;
    private String attendanceNumber;
    private String attendanceCaptureScheme;
    private String attendancePenalisationPolicy;
    private String shiftweeklyOffRule;
    private String shiftAllowancePolicy;
    
    //Organization
    private String businessUnit;
    private String department;
    private String location;
    private String costCenter;
    private String legalEntity;
    private String dottedLineManager;
    private Long reportsTo;
    private Long reportingHr;
    private Long managerOfManager;

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
