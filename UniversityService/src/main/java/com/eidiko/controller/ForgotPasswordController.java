package com.eidiko.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eidiko.entity.ForgotPassword;
import com.eidiko.exception_handler.PasswordMismatchException;
import com.eidiko.exception_handler.UserNotFound;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.ForgotPasswordService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/password")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;


    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassword forgotPassword) {
        try {
            String newPassword = forgotPasswordService.updatePassword(forgotPassword);
            return new CommonResponse<>().prepareSuccessResponseObject("Password updated successfully", newPassword);
        } catch (UserNotFound | PasswordMismatchException e) {
            return new CommonResponse<>().prepareErrorResponseObject(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}

