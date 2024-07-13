package com.eidiko.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.Roles_Table;
@Repository
public interface RolesReposotory extends JpaRepository<Roles_Table, Integer> {

	
	Optional<Roles_Table>findByRoleId(Integer roles_Table);
}
