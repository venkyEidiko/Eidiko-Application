package com.eidiko.controller;

import com.eidiko.entity.WorkFromHomeRequest;
import com.eidiko.repository.WorkFromHomeRequestRepository;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.WorkFromHomeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkFromHomeRequestController {

    @Autowired
    private WorkFromHomeRequestService service;

    @Autowired
    private WorkFromHomeRequestRepository requestRepository;

    @Autowired
    private CommonResponse<WorkFromHomeRequest> commonResponse;

    //requesting for work from home
    @PostMapping("/wfh")
    public ResponseEntity<?> createWFHRequest(@RequestBody WorkFromHomeRequest request) {
        request.setRequestType("Work From Home");
        WorkFromHomeRequest createdRequest = service.createRequest(request);
        return commonResponse.prepareSuccessResponseObject(createdRequest);
    }


    //requesting for onduty
    @PostMapping("/onduty")
    public ResponseEntity<?> createOnDutyRequest(@RequestBody WorkFromHomeRequest request) {
        request.setRequestType("ONDUTY");
        WorkFromHomeRequest createdRequest = service.createRequest(request);
        return commonResponse.prepareSuccessResponseObject(createdRequest);
    }


    //this will get the employeedetails by leave request type
    @GetMapping("/getemployeesdata/{requestType}")
    public ResponseEntity<?> getEmployeeDetailsByRequestTypeAndPresentDate(@PathVariable String requestType) {
        LocalDate presentDate = LocalDate.now();
        List<WorkFromHomeRequest> employeeDetails = requestRepository.findByRequestTypeAndFromDateLessThanOrFromDateGreaterThanEqual(requestType, presentDate, presentDate);

        if (employeeDetails.isEmpty()) {
            return commonResponse.prepareFailedResponse("No records found for request type: " + requestType);
        } else {
            return commonResponse.prepareSuccessResponseObject(employeeDetails);
        }
    }


    //this api is used for getting the employee details who are on leave on present date
    @GetMapping("/onLeaveToday")
    public ResponseEntity<?> getEmployeesOnLeaveToday() {
        LocalDate presentDate = LocalDate.now();
        List<WorkFromHomeRequest> employeeDetails = requestRepository.findByRequestTypeAndFromDateLessThanOrFromDateGreaterThanEqual("Work From Home", presentDate, presentDate);

        if (employeeDetails.isEmpty()) {
            return commonResponse.prepareFailedResponse("No employees on leave today.");
        } else {
            return commonResponse.prepareSuccessResponseObject(employeeDetails);
        }
    }
}
