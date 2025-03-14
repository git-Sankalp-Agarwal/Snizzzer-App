package com.sankalp.notification_service.consumer;

import com.sankalp.follow_service.event.UserFollowedEvent;
import com.sankalp.message_service.event.MessageSendEvent;
import com.sankalp.notification_service.Services.SendNotification;
import com.sankalp.notification_service.clients.MessageServiceClient;
import com.sankalp.notification_service.dto.MessageDeliveredDto;
import com.sankalp.notification_service.entity.MessageNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewMessageNotificationConsumer {

    private final SendNotification sendNotification;
    private final MessageServiceClient messageServiceClient;
    private final ModelMapper mapper;

    @KafkaListener(topics = "message-send-topic")
    public void handleMessageSendEvent(MessageSendEvent messageSendEvent) {
        log.info("handle Message Send Event: handleUserFollowedEvent: {}", messageSendEvent);

        MessageNotification messageNotification = MessageNotification.builder()
                                                                     .senderId(messageSendEvent.getSenderId())
                                                                     .receiverId(messageSendEvent.getReceiverId())
                                                                     .messageSender(messageSendEvent.getMessageSender())
                                                                     .messageContent(messageSendEvent.getMessageContent())
                                                                     .messageId(messageSendEvent.getMessageId())
                                                                     .chatsId(messageSendEvent.getChatsId())
                                                                     .build();


        sendNotification.sendMessageNotification(messageNotification);

        MessageDeliveredDto messageDeliveredDto = new MessageDeliveredDto();
        messageDeliveredDto.setMessageId(messageSendEvent.getMessageId());
        messageDeliveredDto.setChatsId(messageSendEvent.getChatsId());

        messageServiceClient.updateMessageStatus(messageDeliveredDto); // update message status to delivered
    }
}
