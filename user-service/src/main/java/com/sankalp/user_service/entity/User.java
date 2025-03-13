package com.sankalp.user_service.entity;

import com.sankalp.user_service.entity.enums.AccountType;
import com.sankalp.user_service.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "user_roles")
    private Set<Role> roles;

    @CreationTimestamp
    private LocalDateTime userCreatedTime;

    @UpdateTimestamp
    private LocalDateTime userUpdatedTime;

}
