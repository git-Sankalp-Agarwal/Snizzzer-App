package com.sankalp.message_service.services.impl;

import com.sankalp.message_service.auth.UserContextHolder;
import com.sankalp.message_service.dtos.ChatsDto;
import com.sankalp.message_service.dtos.MessageDto;
import com.sankalp.message_service.entity.Chats;
import com.sankalp.message_service.entity.Message;
import com.sankalp.message_service.entity.Participant;
import com.sankalp.message_service.entity.enums.MessageStatus;
import com.sankalp.message_service.repository.MessageRepository;

import com.sankalp.message_service.services.ChatService;
import com.sankalp.message_service.services.MessageService;
import com.sankalp.message_service.services.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;
    private final ParticipantService participantService;
    private final ChatService chatService;

    @Override
    public ChatsDto sendMessage(MessageDto messageDto) {

        Long messageSenderId = UserContextHolder.getCurrentUserId();
        Long messageReceiverId = messageDto.getMessageReceiverId();

        Participant messageSenderParticipant = participantService.fetchParticipantByUserId(messageSenderId);

        Participant messageReceiverParticipant = participantService.fetchParticipantByUserId(messageReceiverId);

        Chats chatsBetweenParticipants = chatService.fetchChatBetweenParticipants(messageSenderParticipant, messageReceiverParticipant);

        if (chatsBetweenParticipants.getMessages() == null) {
            chatsBetweenParticipants.setMessages(new ArrayList<>());
        }

        Message message = Message.builder()
                                 .messageContent(messageDto.getMessageContent())
                                 .messageType(messageDto.getMessageType())
                                 .messageStatus(MessageStatus.SENT)
                                 .build();
        Message savedMessage = messageRepository.save(message);



        chatsBetweenParticipants.getMessages()
                                .add(savedMessage);

        Chats updateChatsWithSentMessage = chatService.updateParticipantsChat(chatsBetweenParticipants);

        return modelMapper.map(updateChatsWithSentMessage, ChatsDto.class);
    }
}
