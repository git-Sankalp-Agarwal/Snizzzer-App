package com.sankalp.user_service.entity;

import com.sankalp.user_service.entity.enums.AccountType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String bio;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;


    @CreationTimestamp
    private LocalDateTime userCreatedTime;

    @UpdateTimestamp
    private LocalDateTime userUpdatedTime;

}
