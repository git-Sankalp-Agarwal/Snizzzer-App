package com.sankalp.tweet_service.controllers;

import com.sankalp.tweet_service.dto.TweetCreateRequestDto;
import com.sankalp.tweet_service.dto.TweetDto;
import com.sankalp.tweet_service.services.TweetLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetLikeController {

    private final TweetLikeService tweetLikeService;

    @PostMapping("/updateLike/{tweetId}")
    public void updateLike(@PathVariable Long tweetId){
        tweetLikeService.updateTweetLike(tweetId);
    }

}
