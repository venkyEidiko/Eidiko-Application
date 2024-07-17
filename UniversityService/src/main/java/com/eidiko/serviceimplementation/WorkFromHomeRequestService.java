package com.eidiko.serviceimplementation;

import com.eidiko.entity.Employee;
import com.eidiko.entity.WorkFromHomeRequest;
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.repository.EmployeeRepo;
import com.eidiko.repository.WorkFromHomeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkFromHomeRequestService {

    @Autowired
    private WorkFromHomeRequestRepository repository;

    @Autowired
    private EmployeeRepo employeeRepo;


//    public List<WorkFromHomeRequest> getApprovedRequestsForToday() {
//        LocalDate today = LocalDate.now();
//        return repository.findByStatusAndToDateGreaterThanEqual("APPROVED", today);
//    }





    //this method is for the notify field
    public WorkFromHomeRequest createRequest(WorkFromHomeRequest request) {
        request.setStatus("PENDING");
        Employee byEmployeeId = employeeRepo.findByEmployeeId(request.getEmployeeID())
                .orElseThrow(() -> new BadRequestException("user not found"));

        String fullName = byEmployeeId.getFirstName() + " " + byEmployeeId.getLastName();

        request.setEmployeeName(fullName);
        Employee employee = fetchEmployeeDetails(request.getNotify());
        if (employee != null) {
            request.setNotify(employee.toString()); // Set the notify field with employee details
        }

        return repository.save(request);
    }

    private Employee fetchEmployeeDetails(String notify) {
        Optional<Employee> employeeOpt;
        switch (determineNotifyType(notify)) {
            case "employeeId":
                employeeOpt = employeeRepo.findByEmployeeId((long) Integer.parseInt(notify));
                break;
            case "phoneNumber":
                employeeOpt = employeeRepo.findByPhoneNu(notify);
                break;
            case "firstName":
                employeeOpt = employeeRepo.findByFirstName(notify);
                break;
            case "lastName":
                employeeOpt = employeeRepo.findByLastName(notify);
                break;
            default:
                employeeOpt = Optional.empty();
                break;
        }
        return employeeOpt.orElse(null);
    }

    private String determineNotifyType(String notify) {
        if (notify.matches("\\d+")) {
            return "employeeId";
        } else if (notify.matches("^\\+?[0-9. ()-]{10,25}$")) {
            return "phoneNumber";
        } else if (notify.contains(" ")) {
            return "name";
        } else {
            // Attempt to determine if it's a first name or last name
            Optional<Employee> firstNameOpt = employeeRepo.findByFirstName(notify);
            if (firstNameOpt.isPresent()) {
                return "firstName";
            }
            Optional<Employee> lastNameOpt = employeeRepo.findByLastName(notify);
            if (lastNameOpt.isPresent()) {
                return "lastName";
            }
            return "unknown";
        }
    }
}
