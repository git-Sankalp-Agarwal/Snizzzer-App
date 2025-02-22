package com.sankalp.user_service.repository;

import com.sankalp.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByEmail(String email);

    boolean existsByEmail(String email);
}
