package com.sankalp.tweet_service.services.impl;

import com.sankalp.tweet_service.entity.Tweet;
import com.sankalp.tweet_service.repository.TweetLikeRepository;
import com.sankalp.tweet_service.repository.TweetRepository;
import com.sankalp.tweet_service.services.TweetLikeService;
import com.sankalp.tweet_service.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetLikeServiceImpl implements TweetLikeService {

    private final TweetService tweetService;
    private final TweetLikeRepository tweetLikeRepository;

    public String likeTweet(Long tweetId){
        Tweet tweet = tweetService.getTweetInternally(tweetId);
        Long userId = 1L;

        boolean isTweetLiked = tweetLikeRepository.existsByUserIdAndTweet(userId, tweet);

        if(isTweetLiked){


            return "Tweet disliked!!!!!!!";
        }



        return "Tweet Liked!!!!!";
    }

    public void disLikeTweet(Long tweetId){

    }


}
