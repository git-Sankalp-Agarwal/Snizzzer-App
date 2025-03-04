package com.sankalp.user_service.dto;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String firstName;
    private String email;
    private String password;
    private String username;
}
