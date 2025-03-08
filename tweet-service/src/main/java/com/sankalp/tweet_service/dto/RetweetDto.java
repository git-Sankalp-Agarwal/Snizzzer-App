package com.sankalp.tweet_service.dto;

import com.sankalp.tweet_service.entity.Tweet;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetweetDto {

    private Long userId;

    private Tweet originalTweet;

    private LocalDateTime reTweetTime;

}
