package com.sankalp.message_service.repository;

import com.sankalp.message_service.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
