package com.sankalp.tweet_service.controllers;

import com.sankalp.tweet_service.annotations.RolesAllowed;
import com.sankalp.tweet_service.dto.RetweetDto;
import com.sankalp.tweet_service.dto.TweetCreateRequestDto;
import com.sankalp.tweet_service.dto.TweetDto;
import com.sankalp.tweet_service.entity.Retweet;
import com.sankalp.tweet_service.services.RetweetService;
import com.sankalp.tweet_service.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;
    private final RetweetService retweetService;

    @RolesAllowed({"ADMIN", "PREMIUM"})
    @PostMapping("/createTweet")
    public ResponseEntity<TweetDto> createNewTweet(@RequestBody TweetCreateRequestDto tweetCreateRequestDto){
        return ResponseEntity.ok(tweetService.createTweet(tweetCreateRequestDto));
    }

    @RolesAllowed({"ADMIN", "PREMIUM", "USER"})
    @GetMapping("/fetchTweet/{tweetId}")
    public ResponseEntity<TweetDto> getTweet(@PathVariable Long tweetId){
    //    return ResponseEntity.ok().body(tweetService.getTweet(tweetId));
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.getTweet(tweetId));
    }

    @RolesAllowed({"ADMIN", "PREMIUM", "USER"})
    @GetMapping("/fetchUserTweets/{userId}")
    public ResponseEntity<List<TweetDto>> getUserTweets(@PathVariable Long userId){
        return ResponseEntity.ok(tweetService.getUserTweets(userId));
    }

    @RolesAllowed({"ADMIN", "PREMIUM"})
    @PostMapping("/createTweet/{tweetId}")
    public ResponseEntity<RetweetDto> createReTweet(@PathVariable Long tweetId){
        return ResponseEntity.ok(retweetService.createRetweet(tweetId));
    }

}
