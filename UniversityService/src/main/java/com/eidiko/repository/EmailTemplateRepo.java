package com.eidiko.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.EmailTemplate;



@Repository
public interface EmailTemplateRepo extends JpaRepository<EmailTemplate, Integer> {

	Optional<EmailTemplate> findByNameOfTemplate(String nameOfTemplate);
}
