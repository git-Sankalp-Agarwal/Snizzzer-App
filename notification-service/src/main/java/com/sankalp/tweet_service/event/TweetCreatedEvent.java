package com.sankalp.tweet_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetCreatedEvent {

    private Long tweetId;

    private Long creatorId;

    private String creatorName;

    private String content;

}
