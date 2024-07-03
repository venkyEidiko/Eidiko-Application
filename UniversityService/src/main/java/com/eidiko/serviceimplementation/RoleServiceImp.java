package com.eidiko.serviceimplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.eidiko.entity.Roles_Table;
import com.eidiko.repository.RolesReposotory;
import com.eidiko.service.RoleInterface;
@Service
public class RoleServiceImp  implements RoleInterface{

	
	@Autowired
	private RolesReposotory rolesReposotory;
	@Override
	public List<Roles_Table> getAllRoles() {
		
		return rolesReposotory.findAll();
	}

}
