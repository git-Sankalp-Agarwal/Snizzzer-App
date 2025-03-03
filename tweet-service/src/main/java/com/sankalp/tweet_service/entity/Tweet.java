package com.sankalp.tweet_service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String tweetCreatorName;

    private Long parentTweetId;     // to check if it is a reply or original tweet

    private Long likeCount = 0L;

    private Long retweetCount = 0L;

    private Long repliesCount = 0L;

    private boolean isReplyTweet = false;

    @CreationTimestamp
    private LocalDateTime tweetCreateTime;

    @UpdateTimestamp
    private LocalDateTime tweetUpdateTime;

}
