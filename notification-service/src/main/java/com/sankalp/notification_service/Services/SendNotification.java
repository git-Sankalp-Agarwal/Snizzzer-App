package com.sankalp.notification_service.Services;

import com.sankalp.notification_service.entity.Notification;
import com.sankalp.notification_service.entity.enums.NotificationType;
import com.sankalp.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendNotification {

    private final NotificationRepository notificationRepository;

    public void send(Long userId, String message, NotificationType notificationType, Long tweetId){
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setTweetId(tweetId);
        notification.setNotificationType(notificationType);

        notificationRepository.save(notification);
        log.info("Notification saved for user: {}", userId);
    }


}
