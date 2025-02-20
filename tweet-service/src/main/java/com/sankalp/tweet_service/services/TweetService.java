package com.sankalp.tweet_service.services;

import com.sankalp.tweet_service.dto.TweetCreateRequestDto;
import com.sankalp.tweet_service.dto.TweetDto;
import com.sankalp.tweet_service.entity.Tweet;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TweetService {
    TweetDto createTweet(TweetCreateRequestDto tweetCreateRequestDto);

    TweetDto getTweet(Long tweetId);

    Tweet getTweetInternally(Long tweetId);

    List<TweetDto> getUserTweets(Long userId);
}
