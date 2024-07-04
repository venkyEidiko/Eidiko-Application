package com.eidiko.serviceimplementation;

import com.eidiko.entity.Employee;
import com.eidiko.entity.ForgotPassword;
import com.eidiko.exception_handler.PasswordMismatchException;
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.repository.EmployeeRepo;
import com.eidiko.service.ForgotPasswordInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordService implements ForgotPasswordInterface {

    @Autowired
    private EmployeeRepo employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String updatePassword(ForgotPassword forgotPassword) throws BadRequestException {
        if (!forgotPassword.getNewPassword().equals(forgotPassword.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        Optional<Employee> userOptional = employeeRepository.findByEmail(forgotPassword.getEmail());
        if (userOptional.isPresent()) {
            Employee employee = userOptional.get();
            
            String encodePassword = passwordEncoder.encode(forgotPassword.getNewPassword());
            
            employee.setPassword(encodePassword);
            employeeRepository.save(employee);
            return forgotPassword.getNewPassword();
        } else {
            throw new BadRequestException("User not found with this emailID");
        }
    }










}
