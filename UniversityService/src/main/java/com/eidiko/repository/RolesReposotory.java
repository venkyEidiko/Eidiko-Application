package com.eidiko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.Roles;
@Repository
public interface RolesReposotory extends JpaRepository<Roles, Integer> {

}
