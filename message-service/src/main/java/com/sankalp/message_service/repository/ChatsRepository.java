package com.sankalp.message_service.repository;

import com.sankalp.message_service.entity.Chats;
import com.sankalp.message_service.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatsRepository extends JpaRepository<Chats, Long> {

    @Query(value = "SELECT c FROM Chats c " +
            "WHERE (c.participantOne = :participant1 AND c.participantTwo = :participant2) " +
            "OR (c.participantOne = :participant2 AND c.participantTwo = :participant1)")
    Optional<Chats> findByParticipants (Participant participant1, Participant participant2);

}
