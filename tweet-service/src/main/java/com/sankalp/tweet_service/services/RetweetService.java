package com.sankalp.tweet_service.services;

import com.sankalp.tweet_service.dto.RetweetDto;
import org.springframework.stereotype.Service;


public interface RetweetService {

    RetweetDto createRetweet(Long tweetId);


}
