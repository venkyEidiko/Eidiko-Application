package com.eidiko.serviceimplementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eidiko.dto.EmpLeaveDto;
import com.eidiko.dto.LeaveSummary;
import com.eidiko.dto.MonthlyLeaveData;
import com.eidiko.entity.EmpLeave;
import com.eidiko.entity.ResponseModel;
import com.eidiko.mapper.Mapper;
import com.eidiko.repository.EmpLeaveRepo;
import com.eidiko.service.EmpLeaveService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Sort;
@Service
@Slf4j
public class EmpLeaveServiceImpl implements EmpLeaveService {

	@Autowired
	private EmpLeaveRepo empLeaveRepo;
	@Autowired
	private Mapper mapper;
	
	static final double totalLeave = 12.0;

	@Override
	public EmpLeaveDto saveEmpLeave(EmpLeaveDto empLeaveDto) {
		EmpLeave empLeave = mapper.empLeaveDtoToEmpLeave(empLeaveDto);
		
		empLeave.setStatus("Pending");
		empLeave.setLeaveDates(LocalDate.now());
		  if (empLeave.getFromDate() != null && empLeave.getToDate() != null) {
	            empLeave.setDurationInDays(1 + ChronoUnit.DAYS.between(empLeave.getFromDate(), empLeave.getToDate()));
	        } else {
	            empLeave.setDurationInDays(0.0);
	        }

		EmpLeave savedEmpLeave = empLeaveRepo.save(empLeave);
		return mapper.empLeaveToEmpLeaveDto(savedEmpLeave);
	}

	@Override
	public EmpLeaveDto updateLeaveByEmployee(Long leaveId,EmpLeaveDto empLeaveDto) {
		EmpLeave empLeave = empLeaveRepo.findById(leaveId).orElseThrow(()-> new UsernameNotFoundException("This Leave Id is not available !"));
	
			
			empLeave.setStatus("Pending");
			empLeave.setFromDate(empLeaveDto.getFromDate());
			empLeave.setToDate(empLeaveDto.getToDate());
			empLeave.setLeaveNote(empLeaveDto.getLeaveNote());
			empLeave.setLeaveType(empLeaveDto.getLeaveType());
			empLeave.setNotifyTo(empLeaveDto.getNotifyTo());
			
			empLeaveRepo.save(empLeave);
			return mapper.empLeaveToEmpLeaveDto(empLeave); 
		
	}
	
	@Override
	public EmpLeaveDto updateLeaveByApprover(Long leaveid, EmpLeaveDto empLeaveDto,String actionTakenBy) {
		EmpLeave empLeave = empLeaveRepo.findById(leaveid).orElseThrow(()-> new UsernameNotFoundException("This Leave Id is not available !"));
		empLeave.setStatus(empLeaveDto.getStatus());
		empLeave.setActionTakenBy(actionTakenBy);
		empLeave.setRejectionReason(empLeaveDto.getRejectionReason());
		return mapper.empLeaveToEmpLeaveDto(empLeaveRepo.save(empLeave));
	}

	@Override
	public EmpLeaveDto getEmpLeaveById(Long leaveId) {
		EmpLeave empLeave = empLeaveRepo.findById(leaveId).orElseThrow(()-> new UsernameNotFoundException("This Leave Id is not available !"));;
		return mapper.empLeaveToEmpLeaveDto(empLeave);
	}

	@Override
	public List<EmpLeaveDto> getAllEmpLeaveByEmpId(Integer pageNumber, Integer pageSize,Long employeeId) {
		int pageSizes=pageSize;
		int pageNumbers=pageNumber;
		Pageable page= PageRequest.of(pageNumbers, pageSizes);
		Page<EmpLeave>leavePage=  this.empLeaveRepo.findAll( page);
List<EmpLeave> empLeaveList=leavePage.getContent();
List<EmpLeaveDto> empLeaveDtoList=empLeaveList.stream().map((empLeave)->this.mapper.empLeaveToEmpLeaveDto(empLeave)).collect(Collectors.toList());
		return empLeaveDtoList;
	}

	@Override
	public List<LeaveSummary> getEmpLeaveSummaryByEmpId(Long employeeId) {
		log.info("Id :{}",employeeId);
		List<EmpLeave> empLeaveList=empLeaveRepo.findAllByEmployeeId(employeeId);

		List<EmpLeave> pendingLeaveList = empLeaveList.stream().filter(leave->leave.getStatus().equals("Pending")).collect(Collectors.toList());
		List<EmpLeave> approvedLeaveList = empLeaveList.stream().filter(leave->leave.getStatus().equals("Approved")).collect(Collectors.toList());
		 // Grouping leaves by type and summing the durations of the leaves
		Map<String, Double> leaveDurationByType = approvedLeaveList.stream()
		        .collect(Collectors.groupingBy(
		                EmpLeave::getLeaveType,
		                Collectors.summingDouble(leave -> {
		                    double duration = leave.getDurationInDays();
		                    if (!leave.getCustomDayStatus().equalsIgnoreCase("Full Day")) {
		                        return duration - 0.5; // Subtract 0.5 days for half day
		                    } else {
		                        return duration; // Full day or other statuses, no change
		                    }
		                })));

	   // Grouping leaves by month
	   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
	   Map<String, Map<String, MonthlyLeaveData>> leaveDataByTypeAndMonth = approvedLeaveList.stream()
	           .collect(Collectors.groupingBy(EmpLeave::getLeaveType,
	                   Collectors.groupingBy(leave -> leave.getFromDate().format(formatter),
	                   Collectors.collectingAndThen(Collectors.toList(), leaves -> {
	                       double totalLeaveTaken = leaves.stream()
	                               .mapToDouble(EmpLeave::getDurationInDays)
	                               .sum();
	                       List<LocalDate> leaveDays = leaves.stream()
	                               .flatMap(leave -> leave.getFromDate().datesUntil(leave.getToDate().plusDays(1)))
	                               .collect(Collectors.toList());
	                       return new MonthlyLeaveData(totalLeaveTaken, leaveDays);
	                   }))));
	   
	   Map<String, Double> totalLeaveByType = new HashMap<>();
	   totalLeaveByType.put("Paid Leave", 12.0); // Example for paid leave
	   totalLeaveByType.put("Other Leave Type", Double.POSITIVE_INFINITY);
	   // Creating a response model to hold the leave details
	   
	   ResponseModel<LeaveSummary> responseModel = new ResponseModel<>();
	   responseModel.setStatus("Success");
	   responseModel.setStatusCode(200);
	   // Constructing the result list
	   List<LeaveSummary> leaveSummaries = leaveDurationByType.entrySet().stream().map(entry -> {
		    String leaveType = entry.getKey();
		    double consumedLeave = entry.getValue();
		    double availableLeave = totalLeaveByType.getOrDefault(leaveType, Double.POSITIVE_INFINITY) - consumedLeave;
		    Map<String, MonthlyLeaveData> monthlyLeaveData = leaveDataByTypeAndMonth.get(leaveType);
		    return new LeaveSummary(leaveType, consumedLeave, availableLeave, totalLeaveByType.getOrDefault(leaveType, 0.0), pendingLeaveList, monthlyLeaveData);
		}).collect(Collectors.toList());

		return leaveSummaries;
	}
	
	
  
	@Override
	public Page<EmpLeave> findByLeaveTypesAndStatuses(Long employeeId, List<String> leaveTypes, List<String> statuses, Pageable pageable) {
        log.info("Fetching records with leaveTypes: {} and statuses: {}", leaveTypes, statuses);

        Page<EmpLeave> leaves = empLeaveRepo.findByEmployeeIdAndLeaveTypeInAndStatusIn(employeeId,leaveTypes, statuses, pageable);
      
        List<EmpLeave> content = leaves.getContent();
        leaves.getTotalElements();
        log.info("Fetched records: {}", leaves);
        return leaves;
    }



@Override
public List<EmpLeaveDto> getEmployeesOnLeaveToday() {
	LocalDate today = LocalDate.now();
	List<EmpLeave> empLeaveList = empLeaveRepo.findByFromDateLessThanEqualAndToDateGreaterThanEqual(today, today);
	return empLeaveList.stream()
			.map(empLeave -> {
				try {
					return mapper.empLeaveToEmpLeaveDto(empLeave);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			})
			.filter(dto -> dto != null)
			.collect(Collectors.toList());
}


	@Override
	public List<EmpLeaveDto> getEmployeeDetailsByRequestType(String leaveType) {
		List<EmpLeave> empLeaveList = empLeaveRepo.findByLeaveType(leaveType);
		return empLeaveList.stream().map(empLeave -> this.mapper.empLeaveToEmpLeaveDto(empLeave)).collect(Collectors.toList());
	}



}
