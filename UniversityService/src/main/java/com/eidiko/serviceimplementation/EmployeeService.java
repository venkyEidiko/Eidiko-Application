package com.eidiko.serviceimplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eidiko.entity.Address;
import com.eidiko.entity.Employee;
//import com.eidiko.entity.Role;
import com.eidiko.entity.Roles;
import com.eidiko.exception_handler.UserNotFound;
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
	public Employee saveEmployee(Employee employee) {

		
		  Roles save2 = rolesReposotory.save(employee.getRole());
		  employee.setRole(save2);
		
		Employee save = employeeRepo.save(employee);

		return save;
	}

	// Employee update method
	@Override
	public Employee updateEmployee(int employeeId, Employee employee) throws UserNotFound {
		
		Employee byEmployeeId = employeeRepo.findByEmployeeId(employeeId)
				.orElseThrow(()-> new UserNotFound("User not found in database"));

		if (byEmployeeId != null) {
			
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

			return employeeRepo.save(byEmployeeId);
		} else {
			
			return null;
		}
	}

	
}
