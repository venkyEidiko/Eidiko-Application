package com.eidiko.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForgotPassword {

    private String email;
    private String newPassword;
    private String confirmPassword;


}
