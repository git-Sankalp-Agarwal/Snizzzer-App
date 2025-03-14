package com.sankalp.message_service.services.impl;

import com.sankalp.message_service.dtos.ChatsDto;
import com.sankalp.message_service.dtos.MessageDeliveredDto;
import com.sankalp.message_service.entity.Chats;
import com.sankalp.message_service.entity.Message;
import com.sankalp.message_service.entity.Participant;
import com.sankalp.message_service.entity.enums.MessageStatus;
import com.sankalp.message_service.exceptions.ResourceNotFoundException;
import com.sankalp.message_service.repository.ChatsRepository;
import com.sankalp.message_service.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatsRepository chatsRepository;
    private final ModelMapper mapper;

    @Override
    public Chats fetchChatBetweenParticipants(Participant participant1, Participant participant2) {
        Chats participantsChats = chatsRepository.findByParticipants(participant1, participant2)
                                                 .orElse(null);

        if (participantsChats == null) {

            participantsChats = createNewChat(participant1, participant2);

        }

        return participantsChats;
    }

    @Override
    public Chats createNewChat(Participant participant1, Participant participant2) {

        Chats newChats = Chats.builder()
                              .participantOne(participant1)
                              .participantTwo(participant2)
                              .build();
        return chatsRepository.save(newChats);
    }

    @Override
    public Chats updateParticipantsChat(Chats updateChats) {
        return chatsRepository.save(updateChats);
    }

    @Override
    public void updateChatMessageStatus(MessageDeliveredDto messageDeliveredDto) {
        Chats chat = chatsRepository.findById(messageDeliveredDto.getChatsId())
                                    .orElseThrow();

        for (Message message : chat.getMessages()) {
            if (message.getId()
                       .equals(messageDeliveredDto.getMessageId())) {
                message.setMessageStatus(MessageStatus.DELIVERED);
                break;
            }
        }
        updateParticipantsChat(chat);
    }

    @Override
    public ChatsDto getChatWithId(Long chatId) {
        Chats chat = chatsRepository.findById(chatId)
                              .orElseThrow(() -> new ResourceNotFoundException("No chat found"));

        return mapper.map(chat, ChatsDto.class);
    }
}
