package com.eidiko.mapping;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eidiko.dto.EmployeeDto;
import com.eidiko.entity.Employee;
@Component
public class ModelMapperClass {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public EmployeeDto employeeToEmploteeDto(Employee employee) {
		return modelMapper.map(employee,EmployeeDto.class);
	}
}
