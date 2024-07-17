package com.eidiko.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.eidiko.dto.EmpLeaveDto;
import com.eidiko.dto.EmployeeDto;
import com.eidiko.entity.EmpLeave;
import com.eidiko.entity.Employee;

@Configuration
public class Mapper {

	@Autowired
	private ModelMapper modelMapper;

	public EmpLeave empLeaveDtoToEmpLeave(EmpLeaveDto empLeaveDto) {
		return modelMapper.map(empLeaveDto, EmpLeave.class);
	}

	public EmpLeaveDto empLeaveToEmpLeaveDto(EmpLeave empLeave) {
		return modelMapper.map(empLeave, EmpLeaveDto.class);
	}

	public EmployeeDto employeeToEmployeeDto(Employee employee) {

		return modelMapper.map(employee, EmployeeDto.class);
	}
}
