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
import com.sankalp.message_service.exceptions.ResourceAccessDeniedException;
import com.sankalp.message_service.exceptions.ResourceNotFoundException;
import com.sankalp.message_service.exceptions.UnauthorizedActionException;
import com.sankalp.message_service.repository.ChatsRepository;
import com.sankalp.message_service.repository.MessageRepository;

import com.sankalp.message_service.services.ChatService;
import com.sankalp.message_service.services.MessageService;
import com.sankalp.message_service.services.ParticipantService;
import jakarta.transaction.Transactional;
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
    private final ChatsRepository chatRepository;
    private final KafkaTemplate<Long, MessageSendEvent> messageSendEventKafkaTemplate;

    @Override
    @Transactional
    public ChatsDto sendMessage(MessageDto messageDto) {

        Long messageSenderId = UserContextHolder.getCurrentUserId();

        Long messageReceiverId = messageDto.getMessageReceiverId();

        if (messageSenderId.equals(messageReceiverId)) {
            throw new IllegalArgumentException("Sender and Receiver participants are same");
        }

        String messageSender = UserContextHolder.getCurrentUserName();

        Participant messageSenderParticipant = participantService.fetchParticipantByUserId(messageSenderId);

        Participant messageReceiverParticipant = participantService.fetchParticipantByUserId(messageReceiverId);

        Chats chatsBetweenParticipants = chatService.fetchChatBetweenParticipants(messageSenderParticipant, messageReceiverParticipant);

        if (chatsBetweenParticipants.getMessages() == null) {
            chatsBetweenParticipants.setMessages(new ArrayList<>());
        }

        Message message = Message.builder()
                                 .messageSenderId(messageSenderId)
                                 .messageSender(messageSender)
                                 .messageContent(messageDto.getMessageContent())
                                 .messageType(messageDto.getMessageType())
                                 .messageStatus(MessageStatus.SENT)
                                 .chat(chatsBetweenParticipants)
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
    @Transactional
    public void updateMessageStatusToDelivered(MessageDeliveredDto messageDeliveredDto) {

        Message message = messageRepository.findById(messageDeliveredDto.getMessageId())
                                           .orElseThrow();
        message.setMessageStatus(MessageStatus.DELIVERED);

        messageRepository.save(message);

        //     chatService.updateChatMessageStatus(messageDeliveredDto);

    }

    @Override
    @Transactional
    public ChatsDto readMessage(Long messageId) {

        Long messageReaderId = UserContextHolder.getCurrentUserId();

        Message message = messageRepository.findById(messageId)
                                           .orElseThrow(() -> new ResourceNotFoundException("Message not found with ID: " + messageId));

        Chats chatsBetweenParticipants = message.getChat();


        if (message.getMessageSenderId()
                   .equals(messageReaderId)) {
            return modelMapper.map(chatsBetweenParticipants, ChatsDto.class);
        }

        Participant messageReaderParticipant = participantService.fetchParticipantByUserId(messageReaderId);


        boolean isParticipantInChat = isParticipantInChat(chatsBetweenParticipants, messageReaderParticipant);

        if (!isParticipantInChat) {
            throw new ResourceAccessDeniedException("You are not allowed to access this chat!");
        }

        if (message.getMessageStatus() != MessageStatus.READ) {
            message.setMessageStatus(MessageStatus.READ);
            messageRepository.save(message);
        }

        return modelMapper.map(chatsBetweenParticipants, ChatsDto.class);
    }

    @Override
    @Transactional
    public ChatsDto deleteMessage(Long messageId) {
        Long messageDeleterId = UserContextHolder.getCurrentUserId();

        Message message = messageRepository.findById(messageId)
                                           .orElseThrow(() -> new ResourceNotFoundException("Message not found with ID: " + messageId));

        if (!message.getMessageSenderId().equals(messageDeleterId)) {
            throw new ResourceAccessDeniedException("You are not authorized to delete this message.");
        }

        Chats chat = message.getChat();

        chat.getMessages().removeIf(msg -> msg.getId().equals(messageId));

        chatRepository.save(chat);

        return modelMapper.map(chat, ChatsDto.class);
    }


    private boolean isParticipantInChat(Chats chat, Participant readerParticipant) {
        Long readerUserId = readerParticipant.getParticipantUserId();
        return readerUserId.equals(chat.getParticipantOne().getParticipantUserId()) ||
                readerUserId.equals(chat.getParticipantTwo().getParticipantUserId());
    }

}
