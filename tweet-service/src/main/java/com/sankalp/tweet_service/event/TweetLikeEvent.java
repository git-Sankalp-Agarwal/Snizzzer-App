package com.sankalp.tweet_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetLikeEvent {

    private Long tweetId;

    private Long creatorId;

    private Long likedByUserId;

    private String likedByName;

}
