package com.eidiko.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.eidiko.dto.EmpLeaveDto;
import com.eidiko.entity.EmpLeave;
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
}
