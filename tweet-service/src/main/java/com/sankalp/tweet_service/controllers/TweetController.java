package com.sankalp.tweet_service.controllers;

import com.sankalp.tweet_service.dto.TweetCreateRequestDto;
import com.sankalp.tweet_service.dto.TweetDto;
import com.sankalp.tweet_service.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @PostMapping("/createTweet")
    public ResponseEntity<TweetDto> createNewTweet(@RequestBody TweetCreateRequestDto tweetCreateRequestDto){
        return ResponseEntity.ok(tweetService.createTweet(tweetCreateRequestDto));
    }

    @GetMapping("/fetchTweet/{tweetId}")
    public ResponseEntity<TweetDto> getTweet(@PathVariable Long tweetId){
    //    return ResponseEntity.ok().body(tweetService.getTweet(tweetId));
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.getTweet(tweetId));
    }

    @GetMapping("/fetchUserTweets/{userId}")
    public ResponseEntity<List<TweetDto>> getUserTweets(@PathVariable Long userId){
        return ResponseEntity.ok(tweetService.getUserTweets(userId));
    }


}
