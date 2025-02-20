package com.sankalp.tweet_service.services.impl;

import ch.qos.logback.core.read.ListAppender;
import com.sankalp.tweet_service.dto.TweetCreateRequestDto;
import com.sankalp.tweet_service.dto.TweetDto;
import com.sankalp.tweet_service.entity.Tweet;
import com.sankalp.tweet_service.exceptions.ResourceNotFoundException;
import com.sankalp.tweet_service.repository.TweetRepository;
import com.sankalp.tweet_service.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final ModelMapper mapper;

    @Override
    public TweetDto createTweet(TweetCreateRequestDto tweetCreateRequestDto) {
        Tweet tweet = mapper.map(tweetCreateRequestDto, Tweet.class);
        tweet.setId(null);
        tweet.setReplyTweet(false);
        tweet.setLikeCount(0L);
        tweet.setRepliesCount(0L);
        tweet.setUserId(1L);
        tweet.setRetweetCount(0L);
        Tweet savedTweet = tweetRepository.save(tweet);

        return mapper.map(savedTweet, TweetDto.class);
    }

    @Override
    public TweetDto getTweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                                     .orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id::: " + tweetId));

        return mapper.map(tweet, TweetDto.class);

    }

    @Override
    public Tweet getTweetInternally(Long tweetId) {
        return tweetRepository.findById(tweetId)
                              .orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id::: " + tweetId));
    }

    @Override
    public List<TweetDto> getUserTweets(Long userId) {
        List<Tweet> userTweets = tweetRepository.findByUserId(userId)
                                                .orElseThrow(() -> new ResourceNotFoundException("No tweets were found with userId::: " + userId));


        return userTweets.stream()
                         .map(userTweet -> mapper.map(userTweet, TweetDto.class))
                         .collect(Collectors.toList());

    }

}
