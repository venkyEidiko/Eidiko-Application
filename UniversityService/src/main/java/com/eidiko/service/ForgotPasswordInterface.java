package com.eidiko.service;


import com.eidiko.entity.ForgotPassword;
import com.eidiko.exception_handler.UserNotFound;

public interface ForgotPasswordInterface {

    public void updatePassword(ForgotPassword forgotPassword) throws UserNotFound;

//    String updatePassword(ForgotPassword forgotPassword) throws UserNotFound;

}

