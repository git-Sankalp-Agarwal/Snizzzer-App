package com.sankalp.message_service.services;

import com.sankalp.message_service.dtos.ChatsDto;
import com.sankalp.message_service.dtos.MessageDeliveredDto;
import com.sankalp.message_service.entity.Chats;
import com.sankalp.message_service.entity.Participant;

public interface ChatService {

    Chats fetchChatBetweenParticipants(Participant participant1, Participant participant2);

    Chats createNewChat(Participant participant1, Participant participant2);

    Chats updateParticipantsChat(Chats chats);

    void updateChatMessageStatus(MessageDeliveredDto messageDeliveredDto);

    ChatsDto getChatWithId(Long chatId);
}
