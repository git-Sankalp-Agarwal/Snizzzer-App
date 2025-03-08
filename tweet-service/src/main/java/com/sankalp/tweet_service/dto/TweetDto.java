package com.sankalp.tweet_service.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetDto implements Serializable {

    private Long id;

    private String content;

    private Long userId;

    private String tweetCreatorName;

    private Long parentTweetId; // to check if it is a reply or original tweet

    private Long likeCount;

    private Long retweetCount;

    private Long repliesCount;

    private boolean isReplyTweet;

    private LocalDateTime tweetCreateTime;

    private LocalDateTime tweetUpdateTime;


}
