package com.sankalp.user_service.dto;

import com.sankalp.user_service.entity.enums.AccountType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String email;
    private String username;
    private AccountType accountType;
    private String imageUrl;
}
