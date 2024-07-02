package com.eidiko.serviceimplementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eidiko.entity.Employee;
import com.eidiko.entity.ShiftRequest;
import com.eidiko.repository.EmployeeRepo;
import com.eidiko.repository.ShiftRequestRepo;

@Service
public class ShiftRequestService {

	@Autowired
	private ShiftRequestRepo shiftRequestRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	// raise shift request (saves shift request in the in shftrequest db)
	public String saveRequest(Long employeeId, ShiftRequest shiftRequest) {
		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found in DB!!"));

		// Check for already req there or not.
		LocalDate fromDate = shiftRequest.getFromDate();
		LocalDate toDate = shiftRequest.getToDate();
		List<ShiftRequest> overlappingRequests = shiftRequestRepo
				.findByEmployeeEmployeeIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(employeeId, toDate,
						fromDate);

		if (!overlappingRequests.isEmpty()) {
			throw new RuntimeException("YOU ALREADY RAISED REQUEST WITH THIS DATES:  " + shiftRequest.getFromDate()
					+ "  TO  " + shiftRequest.getToDate());
		}

		shiftRequest.setEmployee(employee);
		shiftRequestRepo.save(shiftRequest);
		return "Shift request saved successfully.";
	}

	// updtates shift in the employee table
	public String approveShiftChange(Long employeeId) {

		List<ShiftRequest> shiftRequests = shiftRequestRepo.findByEmployeeEmployeeId(employeeId);

		if (shiftRequests.isEmpty()) {
			throw new RuntimeException("ShiftRequest not found for the employee");
		}

		// Use the most recent ShiftRequest (you may need to adjust the logic based on
		// your requirements)
		ShiftRequest shiftRequest = shiftRequests.get(0);

		Employee employee = shiftRequest.getEmployee();

		System.out.println("----------------------->" + employee.getFirstName());

		// Update the Employee's shift with the new shift value from the ShiftRequest
		employee.setShift(shiftRequest.getNewShift());

		// Save the updated Employee entity to the repository to persist the change
		employeeRepo.save(employee);

		// shiftRequests.forEach(sr -> sr.setStatus("Approved"));
		shiftRequestRepo.saveAll(shiftRequests);

		return "Shift updated for employee: " + employeeId;
	}
	
	//delete shift request
	public String deleteShiftRequestsByEmployeeId(Long employeeId) {
        // Check if the employee exists
		List<ShiftRequest> shiftRequests = shiftRequestRepo.findByEmployeeEmployeeId(employeeId);

		if (shiftRequests.isEmpty()) {
			throw new RuntimeException("ShiftRequest not found for the employee: "+employeeId);
		}
        // Delete the shift requests
        shiftRequestRepo.deleteByEmployeeEmployeeId(employeeId);
	

        return "Shift requests deleted for employee: " + employeeId;
    }

}
