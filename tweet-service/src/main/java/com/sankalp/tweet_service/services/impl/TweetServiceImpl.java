package com.sankalp.tweet_service.services.impl;

import com.sankalp.tweet_service.auth.UserContextHolder;
import com.sankalp.tweet_service.clients.FollowersClient;
import com.sankalp.tweet_service.dto.TweetCreateRequestDto;
import com.sankalp.tweet_service.dto.TweetDto;
import com.sankalp.tweet_service.entity.Tweet;
import com.sankalp.tweet_service.event.TweetCreatedEvent;
import com.sankalp.tweet_service.exceptions.ResourceNotFoundException;
import com.sankalp.tweet_service.repository.TweetRepository;
import com.sankalp.tweet_service.services.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final ModelMapper mapper;
    private final FollowersClient followersClient;
    private final String USER_TWEETS_CACHE_NAME = "getUserTweets";
    private final String TWEETS_CACHE_NAME = "getTweets";
    private final KafkaTemplate<Long, TweetCreatedEvent> postCreateKafkaTemplate;

    @Override
    @CachePut(cacheNames = TWEETS_CACHE_NAME, key = "#result.id")
    @CacheEvict(cacheNames = USER_TWEETS_CACHE_NAME, key = "#result.userId")
    public TweetDto createTweet(TweetCreateRequestDto tweetCreateRequestDto) {

        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Fetched User id from user context::: {}", userId);

        String userName = UserContextHolder.getCurrentUserName();
        log.info("Fetched User Name from user context::: {}", userName);

        Tweet tweet = mapper.map(tweetCreateRequestDto, Tweet.class);

        tweet.setId(null);
        tweet.setTweetCreatorName(userName);
        tweet.setUserId(userId);
        Tweet savedTweet = tweetRepository.save(tweet);

        log.info("Creating of tweet event to send notification");

        TweetCreatedEvent tweetCreatedEvent = new TweetCreatedEvent();
        tweetCreatedEvent.setCreatorId(userId);
        tweetCreatedEvent.setCreatorName(userName);
        tweetCreatedEvent.setTweetId(savedTweet.getId());
        tweetCreatedEvent.setContent(tweetCreateRequestDto.getContent());

        postCreateKafkaTemplate.send("tweet-create-topic", tweetCreatedEvent);

        return mapper.map(savedTweet, TweetDto.class);
    }

    @Override
    @Cacheable(cacheNames = TWEETS_CACHE_NAME, key = "#tweetId")
    public TweetDto getTweet(Long tweetId) {

        log.info("Fetching tweet from repository: {}", tweetId);

        Tweet tweet = tweetRepository.findById(tweetId)
                                     .orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id::: " + tweetId));

        return mapper.map(tweet, TweetDto.class);

    }

    @Override
    public Tweet getTweetInternally(Long tweetId) {

        log.info("Fetching tweet internally for method from repository: {}", tweetId);

        return tweetRepository.findById(tweetId)
                              .orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id::: " + tweetId));
    }

    @Override
    public void updateTweetLikeCount(Tweet tweet, String method) {
        if (method.equals("like")) {
            tweet.setLikeCount(tweet.getLikeCount() + 1);
        } else {
            tweet.setLikeCount(tweet.getLikeCount() - 1);
        }
        tweetRepository.save(tweet);
    }

    @Override
    @Cacheable(cacheNames = USER_TWEETS_CACHE_NAME, key = "#userId")
    public List<TweetDto> getUserTweets(Long userId) {
        log.info("Fetching tweets for user from repository: {}", userId);

        List<Tweet> userTweets = tweetRepository.findByUserId(userId)
                                                .orElseThrow(() -> new ResourceNotFoundException("No tweets were found with userId::: " + userId));


        return userTweets.stream()
                         .map(userTweet -> mapper.map(userTweet, TweetDto.class))
                         .collect(Collectors.toList());

    }

}
