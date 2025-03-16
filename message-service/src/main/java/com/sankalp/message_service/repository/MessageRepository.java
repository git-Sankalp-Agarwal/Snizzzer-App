package com.sankalp.message_service.repository;

import com.sankalp.message_service.entity.Chats;
import com.sankalp.message_service.entity.Message;
import com.sankalp.message_service.entity.enums.MessageStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "DELETE FROM Message m " +
            "WHERE m.id = :messageId AND m.messageSenderId = :deleterId " +
            "RETURNING m.chat", nativeQuery = true)
    Optional<Chats> deleteMessageAndReturnChat(@Param("messageId") Long messageId,
                                               @Param("deleterId") Long deleterId);


    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageStatus = :status " +
            "WHERE m.chat.id = :chatId " +
            "AND m.messageStatus = 'DELIVERED' " +
            "AND m.messageSenderId != :currentUserId")
    int updateMessageStatus(@Param("chatId") Long chatId,
                           @Param("status") MessageStatus status,
                           @Param("currentUserId") Long currentUserId);


}
