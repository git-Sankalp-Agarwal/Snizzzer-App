package com.sankalp.message_service.repository;

import com.sankalp.message_service.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByParticipantUserId(Long userId);
}
