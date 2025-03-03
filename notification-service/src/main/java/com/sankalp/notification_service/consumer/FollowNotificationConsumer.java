package com.sankalp.notification_service.consumer;

import com.sankalp.follow_service.event.UserFollowedEvent;
import com.sankalp.notification_service.Services.SendNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowNotificationConsumer {

    private final SendNotification sendNotification;

    @KafkaListener(topics = "user-followed-topic")
    public void handleUserFollowedEvent(UserFollowedEvent userFollowedEvent) {
        log.info("handle User followed Event: handleUserFollowedEvent: {}", userFollowedEvent);
        String message = "Hey, "+ userFollowedEvent.getReceiverId()+" you have a new follower!!!" + userFollowedEvent.getSenderName() +" has just started following you. Check it out!";
        sendNotification.sendFollowNotification(userFollowedEvent.getSenderId(),userFollowedEvent.getReceiverId(), message);
    }

}
