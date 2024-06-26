package com.eidiko.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eidiko.dto.EmpLeaveDto;
import com.eidiko.dto.LeaveSummary;
import com.eidiko.entity.EmpLeave;

public interface EmpLeaveService {

	 public EmpLeaveDto saveEmpLeave(EmpLeaveDto empLeaveDto);

	 public EmpLeaveDto updateLeaveByEmployee(Long leaveid,EmpLeaveDto empLeaveDto) ;
	 public EmpLeaveDto updateLeaveByApprover(Long leaveid,EmpLeaveDto empLeaveDto,String actionTakenBy) ;
	 public EmpLeaveDto getEmpLeaveById(Long leaveId);

	 public List<EmpLeaveDto> getAllEmpLeaveByEmpId(Integer pageNumber, Integer pageSize,Long employeeId);
	 
	 public List<LeaveSummary> getEmpLeaveSummaryByEmpId(Long employeeId);

	 
	 
	 
	 public Page<EmpLeave> findByLeaveTypesAndStatuses(List<String> leaveTypes, List<String> statuses, Pageable pageable);





	public List<EmpLeaveDto> getEmployeesOnLeaveToday();

	public List<EmpLeaveDto> getEmployeeDetailsByRequestType(String leaveType);

}
