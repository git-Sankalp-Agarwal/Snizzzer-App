package com.sankalp.tweet_service.services.impl;

import com.sankalp.tweet_service.auth.UserContextHolder;
import com.sankalp.tweet_service.entity.Tweet;
import com.sankalp.tweet_service.entity.TweetLike;
import com.sankalp.tweet_service.event.TweetLikeEvent;
import com.sankalp.tweet_service.repository.TweetLikeRepository;
import com.sankalp.tweet_service.repository.TweetRepository;
import com.sankalp.tweet_service.services.TweetLikeService;
import com.sankalp.tweet_service.services.TweetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TweetLikeServiceImpl implements TweetLikeService {

    private final TweetService tweetService;
    private final TweetLikeRepository tweetLikeRepository;
    private final KafkaTemplate<Long, TweetLikeEvent> tweetLikeEventKafkaTemplate;

    @Override
    @Transactional
    public void updateTweetLike(Long tweetId) {
        Tweet tweet = tweetService.getTweetInternally(tweetId);
        Long userId = UserContextHolder.getCurrentUserId();
        String userName = UserContextHolder.getCurrentUserName();
        TweetLike tweetLike = tweetLikeRepository.findByUserIdAndTweet(userId, tweet)
                                                 .orElse(null);
        Long tweetCreatorId = tweet.getUserId();

        if (tweetLike != null) {
            tweetService.updateTweetLikeCount(tweet, "dislike");
            disLikeTweet(tweetLike);
            return;
        }
        tweetService.updateTweetLikeCount(tweet, "like");
        TweetLike tweetLike1 = TweetLike.builder()
                                        .userId(userId)
                                        .likedByUserName(userName)
                                        .tweet(tweet)
                                        .build();
        tweetLikeRepository.save(tweetLike1);

        TweetLikeEvent tweetLikeEvent = new TweetLikeEvent();

        tweetLikeEvent.setLikedByUserId(userId);
        tweetLikeEvent.setTweetId(tweetId);
        tweetLikeEvent.setLikedByName(userName);
        tweetLikeEvent.setCreatorId(tweetCreatorId);

        tweetLikeEventKafkaTemplate.send("tweet-like-topic", tweetId, tweetLikeEvent);

    }

    @Transactional
    public void disLikeTweet(TweetLike tweetLike) {
        tweetLikeRepository.delete(tweetLike);
    }


}
