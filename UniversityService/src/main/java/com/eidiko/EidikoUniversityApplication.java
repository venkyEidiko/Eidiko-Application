package com.eidiko;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.eidiko.entity.Roles_Table;
//import com.eidiko.entity.Roles;
import com.eidiko.repository.RolesReposotory;

@SpringBootApplication
public class EidikoUniversityApplication {

	public static void main(String[] args) {
		SpringApplication.run(EidikoUniversityApplication.class, args);
		
	}

	
	
//	@Autowired
//	private RolesReposotory roleRepository;
//	
//	
//	@Bean
//    public CommandLineRunner roleData() {
//        return args -> {
//            // Insert static roles
//          
//            roleRepository.save(new Roles_Table(201,"ADMIN"));
//            roleRepository.save(new Roles_Table(202,"EMPLOYEE"));
//            roleRepository.save(new Roles_Table(203,"HR"));
//        	
//        	
//        };
//        
//        
//    }
	
}
