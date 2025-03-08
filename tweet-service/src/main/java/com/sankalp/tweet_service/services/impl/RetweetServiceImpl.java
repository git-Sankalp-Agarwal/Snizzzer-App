package com.sankalp.tweet_service.services.impl;

import com.sankalp.tweet_service.auth.UserContextHolder;
import com.sankalp.tweet_service.dto.RetweetDto;
import com.sankalp.tweet_service.entity.Retweet;
import com.sankalp.tweet_service.entity.Tweet;
import com.sankalp.tweet_service.repository.RetweetRepository;
import com.sankalp.tweet_service.services.RetweetService;
import com.sankalp.tweet_service.services.TweetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetweetServiceImpl implements RetweetService {

    private final TweetService tweetService;
    private final RetweetRepository retweetRepository;
    private final ModelMapper mapper;
    private final String TWEETS_CACHE_NAME = "getTweets";

    @Override
    @Transactional
    public RetweetDto createRetweet(Long tweetId) {

        Tweet originalTweet = tweetService.getTweetInternally(tweetId);
        Long userId = UserContextHolder.getCurrentUserId();

        Retweet retweet = Retweet.builder()
                                 .originalTweet(originalTweet)
                                 .userId(userId)
                                 .build();
        Retweet savedRetweet = retweetRepository.save(retweet);

        Long currentTweetCount = originalTweet.getRetweetCount();
        originalTweet.setRetweetCount(currentTweetCount + 1);
        tweetService.updateTweet(tweetId);

        return mapper.map(savedRetweet, RetweetDto.class);


    }
}
