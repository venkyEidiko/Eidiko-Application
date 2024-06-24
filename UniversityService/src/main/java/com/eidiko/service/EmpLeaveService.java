package com.eidiko.service;

import java.util.List;

import com.eidiko.dto.EmpLeaveDto;
import com.eidiko.dto.LeaveSummary;

public interface EmpLeaveService {

	 public EmpLeaveDto saveEmpLeave(EmpLeaveDto empLeaveDto);

	 public EmpLeaveDto updateLeaveByEmployee(Long leaveid,EmpLeaveDto empLeaveDto) ;
	 public EmpLeaveDto updateLeaveByApprover(Long leaveid,EmpLeaveDto empLeaveDto,String actionTakenBy) ;
	 public EmpLeaveDto getEmpLeaveById(Long leaveId);

	 public List<EmpLeaveDto> getAllEmpLeaveByEmpId(Integer pageNumber, Integer pageSize,Long employeeId);
	 
	 public List<LeaveSummary> getEmpLeaveSummaryByEmpId(Long employeeId);




	public List<EmpLeaveDto> getEmployeesOnLeaveToday();

	public List<EmpLeaveDto> getEmployeeDetailsByRequestType(String leaveType);
}
