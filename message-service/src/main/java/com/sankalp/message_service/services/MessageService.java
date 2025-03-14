package com.sankalp.message_service.services;

import com.sankalp.message_service.dtos.ChatsDto;
import com.sankalp.message_service.dtos.MessageDeliveredDto;
import com.sankalp.message_service.dtos.MessageDto;
import com.sankalp.message_service.entity.Chats;

public interface MessageService {

    ChatsDto sendMessage(MessageDto messageDto);


    void updateMessageStatusToDelivered(MessageDeliveredDto messageDeliveredDto);

    ChatsDto readMessage(Long messageSenderId, Long messageId);
}
