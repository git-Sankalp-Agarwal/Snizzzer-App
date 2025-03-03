package com.sankalp.notification_service.consumer;

import com.sankalp.notification_service.Services.SendNotification;
import com.sankalp.notification_service.clients.FollowersClient;
import com.sankalp.notification_service.dto.PersonDto;
import com.sankalp.notification_service.entity.enums.NotificationType;
import com.sankalp.tweet_service.event.TweetCreatedEvent;
import com.sankalp.tweet_service.event.TweetLikeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostNotificationConsumer {

    private final FollowersClient followersClient;
    private final SendNotification sendNotification;

    @KafkaListener(topics = "tweet-create-topic")
    public void handleTweetCreateEvent(TweetCreatedEvent tweetCreatedEvent){

        List<PersonDto> followerList = followersClient.getFollowers(tweetCreatedEvent.getCreatorId(), tweetCreatedEvent.getCreatorName());
        String tweetCreateNotificationMessage = tweetCreatedEvent.getCreatorName() + " has tweeted!!!! Check it Out!! has id:: "+ tweetCreatedEvent.getCreatorId();
        for(PersonDto follower: followerList){
            sendNotification.sendTweetNotification(follower.getUserId(), tweetCreateNotificationMessage, NotificationType.TWEET_CREATE, tweetCreatedEvent.getTweetId());
        }


    }

    @KafkaListener(topics = "tweet-like-topic")
    public void handleTweetLikeEvent(TweetLikeEvent tweetLikeEvent){
        String tweetLikeNotificationMessage = tweetLikeEvent.getLikedByName() + " has liked your tweet!!!! has id:: "+ tweetLikeEvent.getLikedByUserId();

            sendNotification.sendTweetNotification(tweetLikeEvent.getCreatorId(), tweetLikeNotificationMessage, NotificationType.TWEET_LIKE, tweetLikeEvent.getTweetId());

    }


}
