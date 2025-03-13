package com.sankalp.message_service.services;

import com.sankalp.message_service.entity.Chats;
import com.sankalp.message_service.entity.Participant;

public interface ChatService {

    Chats fetchChatBetweenParticipants(Participant participant1, Participant participant2);

    Chats createNewChat(Participant participant1, Participant participant2);

    Chats updateParticipantsChat(Chats chats);

}
