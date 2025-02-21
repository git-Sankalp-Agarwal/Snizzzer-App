package com.sankalp.tweet_service.services.impl;

import com.sankalp.tweet_service.entity.Tweet;
import com.sankalp.tweet_service.entity.TweetLike;
import com.sankalp.tweet_service.repository.TweetLikeRepository;
import com.sankalp.tweet_service.repository.TweetRepository;
import com.sankalp.tweet_service.services.TweetLikeService;
import com.sankalp.tweet_service.services.TweetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetLikeServiceImpl implements TweetLikeService {

    private final TweetService tweetService;
    private final TweetLikeRepository tweetLikeRepository;

    @Override
    @Transactional
    public void updateTweetLike(Long tweetId) {
        Tweet tweet = tweetService.getTweetInternally(tweetId);
        Long userId = 1L;

        TweetLike tweetLike = tweetLikeRepository.findByUserIdAndTweet(userId, tweet)
                                                 .orElse(null);
        if (tweetLike != null) {
            tweetService.updateTweetLikeCount(tweet, "dislike");
            disLikeTweet(tweetLike);
            return;
        }
        tweetService.updateTweetLikeCount(tweet, "like");
        TweetLike tweetLike1 = TweetLike.builder()
                                        .userId(userId)
                                        .tweet(tweet)
                                        .build();
        tweetLikeRepository.save(tweetLike1);

    }
    @Transactional
    public void disLikeTweet(TweetLike tweetLike) {
        tweetLikeRepository.delete(tweetLike);
    }


}
