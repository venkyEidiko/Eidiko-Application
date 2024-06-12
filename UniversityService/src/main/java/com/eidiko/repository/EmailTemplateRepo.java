package com.eidiko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.EmailTemplate;



@Repository
public interface EmailTemplateRepo extends JpaRepository<EmailTemplate, Integer> {

}
