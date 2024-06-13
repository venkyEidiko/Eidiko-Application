package com.eidiko.serviceimplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eidiko.entity.Address;
import com.eidiko.entity.Employee;

import com.eidiko.entity.Roles;
import com.eidiko.exception_handler.SaveFailureException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.repository.EmployeeRepo;
import com.eidiko.repository.RolesReposotory;
import com.eidiko.service.EmployeeInterface;

@Service
public class EmployeeService implements EmployeeInterface {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private RolesReposotory rolesReposotory;

	// save the employee details
	@Override
	public String saveEmployee(Employee employee) throws SaveFailureException {

		Roles save2 = rolesReposotory.save(employee.getRole());
		employee.setRole(save2);

		Employee save = employeeRepo.save(employee);

		if (save != null && save.getId() != 0) {
			return "User record has been successfully created.";
		} else {
			//throw new SaveFailureException("Failed to create user record.");
			return "Failed to create user record";
		}

	}

	@Override
	public String updateEmployee(int employeeId, Employee employee) throws UserNotFoundException, SaveFailureException {
		Employee byEmployeeId = employeeRepo.findByEmployeeId(employeeId)
				.orElseThrow(() -> new UserNotFoundException("User not found in database"));

		byEmployeeId.setEmail(employee.getEmail());
		byEmployeeId.setFirstName(employee.getFirstName());
		byEmployeeId.setGender(employee.getGender());
		byEmployeeId.setLastName(employee.getLastName());
		byEmployeeId.setRole(employee.getRole());

		// Update addresses
		List<Address> addresses = employee.getAddresses();
		if (addresses != null) {
			// Update existing addresses or add new addresses
			for (Address address : addresses) {
				address.setEmployee(byEmployeeId);
			}
			byEmployeeId.setAddresses(addresses);
		}

		Employee updatedEmployee = employeeRepo.save(byEmployeeId);

		if (updatedEmployee.getId() != 0) {

			return "User record has been updated ";

		} else {
			return "Failed to save the updated employee record";
		}

	}

	public Employee getByEmail(String emial) throws UserNotFoundException {

		Employee emp = employeeRepo.findByEmail(emial).orElseThrow(() -> new UserNotFoundException("User not found"));

		return emp;

	}

}