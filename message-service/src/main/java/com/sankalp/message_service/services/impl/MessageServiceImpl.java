package com.sankalp.message_service.services.impl;

import com.sankalp.message_service.auth.UserContextHolder;
import com.sankalp.message_service.dtos.ChatsDto;
import com.sankalp.message_service.dtos.MessageDeliveredDto;
import com.sankalp.message_service.dtos.MessageDto;
import com.sankalp.message_service.entity.Chats;
import com.sankalp.message_service.entity.Message;
import com.sankalp.message_service.entity.Participant;
import com.sankalp.message_service.entity.enums.MessageStatus;
import com.sankalp.message_service.event.MessageSendEvent;
import com.sankalp.message_service.repository.MessageRepository;

import com.sankalp.message_service.services.ChatService;
import com.sankalp.message_service.services.MessageService;
import com.sankalp.message_service.services.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;
    private final ParticipantService participantService;
    private final ChatService chatService;
    private final KafkaTemplate<Long, MessageSendEvent> messageSendEventKafkaTemplate;

    @Override
    public ChatsDto sendMessage(MessageDto messageDto) {

        Long messageSenderId = UserContextHolder.getCurrentUserId();
        String messageSender = UserContextHolder.getCurrentUserName();
        Long messageReceiverId = messageDto.getMessageReceiverId();

        Participant messageSenderParticipant = participantService.fetchParticipantByUserId(messageSenderId);

        Participant messageReceiverParticipant = participantService.fetchParticipantByUserId(messageReceiverId);

        Chats chatsBetweenParticipants = chatService.fetchChatBetweenParticipants(messageSenderParticipant, messageReceiverParticipant);

        if (chatsBetweenParticipants.getMessages() == null) {
            chatsBetweenParticipants.setMessages(new ArrayList<>());
        }

        Message message = Message.builder()
                                 .messageSender(messageSender)
                                 .messageContent(messageDto.getMessageContent())
                                 .messageType(messageDto.getMessageType())
                                 .messageStatus(MessageStatus.SENT)
                                 .build();
        Message savedMessage = messageRepository.save(message);


        chatsBetweenParticipants.getMessages()
                                .add(savedMessage);

        chatsBetweenParticipants.setLastMessageAt(LocalDateTime.now());

        Chats updateChatsWithSentMessage = chatService.updateParticipantsChat(chatsBetweenParticipants);

        MessageSendEvent messageSendEvent = MessageSendEvent.builder()
                                                            .senderId(messageSenderId)
                                                            .receiverId(messageReceiverId)
                                                            .messageId(savedMessage.getId())
                                                            .messageContent(savedMessage.getMessageContent())
                                                            .chatsId(updateChatsWithSentMessage.getId())
                                                            .messageSender(messageSender)
                                                            .build();

        messageSendEventKafkaTemplate.send("message-send-topic", savedMessage.getId(), messageSendEvent);

        return modelMapper.map(updateChatsWithSentMessage, ChatsDto.class);
    }

    @Override
    public void updateMessageStatusToDelivered(MessageDeliveredDto messageDeliveredDto) {

        Message message = messageRepository.findById(messageDeliveredDto.getMessageId())
                                           .orElseThrow();
        message.setMessageStatus(MessageStatus.DELIVERED);

        messageRepository.save(message);

   //     chatService.updateChatMessageStatus(messageDeliveredDto);

    }

    @Override
    public ChatsDto readMessage(Long messageSenderId, Long messageId) {

        Long messageReaderId = UserContextHolder.getCurrentUserId();

        Participant messageSenderParticipant = participantService.fetchParticipantByUserId(messageSenderId);

        Participant messageReaderParticipant = participantService.fetchParticipantByUserId(messageReaderId);

        Chats chatsBetweenParticipants = chatService.fetchChatBetweenParticipants(messageSenderParticipant, messageReaderParticipant);

        Message message = messageRepository.findById(messageId)
                                           .orElseThrow();
        message.setMessageStatus(MessageStatus.READ);

        messageRepository.save(message);

        return modelMapper.map(chatsBetweenParticipants, ChatsDto.class);
    }
}
