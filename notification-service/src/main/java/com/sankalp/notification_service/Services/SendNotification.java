package com.sankalp.notification_service.Services;

import com.sankalp.notification_service.entity.FollowNotifications;
import com.sankalp.notification_service.entity.TweetNotifications;
import com.sankalp.notification_service.entity.enums.NotificationType;
import com.sankalp.notification_service.repository.FollowNotificationsRepository;
import com.sankalp.notification_service.repository.TweetNotificationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendNotification {

    private final TweetNotificationsRepository tweetNotificationsRepository;
    private final FollowNotificationsRepository followNotificationsRepository;
    public void sendTweetNotification(Long userId, String message, NotificationType notificationType, Long tweetId){
        TweetNotifications notification = new TweetNotifications();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setTweetId(tweetId);
        notification.setNotificationType(notificationType);

        tweetNotificationsRepository.save(notification);
        log.info("Tweet Notification saved for user: {}", userId);
    }

    public void sendFollowNotification(Long senderId, Long receiverId, String message){
        FollowNotifications notification = new FollowNotifications();
        notification.setSenderId(senderId);
        notification.setReceiverId(receiverId);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.FOLLOW);

        followNotificationsRepository.save(notification);

        log.info("Follow Notification saved for sendId:: {} and receiverId :: {} ", senderId,receiverId);
    }


}
