package com.eidiko.controller;

import com.eidiko.entity.ForgotPassword;
import com.eidiko.exception_handler.UserNotFound;
import com.eidiko.serviceimplementation.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/password")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;


    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPassword forgotPassword) throws UserNotFound {
        forgotPasswordService.updatePassword(forgotPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

}
