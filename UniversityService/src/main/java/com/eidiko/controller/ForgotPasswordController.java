package com.eidiko.controller;


import com.eidiko.entity.ForgotPassword;
import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.PasswordMismatchException;
import com.eidiko.exception_handler.UserNotFound;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/password")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;


    @Autowired
    private CommonResponse commonResponse;

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassword forgotPassword) {
        try {
            String newPassword = forgotPasswordService.updatePassword(forgotPassword);
            return commonResponse.prepareSuccessResponseObject("Password updated successfully", newPassword);
        } catch (UserNotFound | PasswordMismatchException e) {
            return commonResponse.prepareErrorResponseObject(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}

