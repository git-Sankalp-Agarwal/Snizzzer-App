package com.sankalp.tweet_service.repository;

import com.sankalp.tweet_service.entity.Tweet;
import com.sankalp.tweet_service.entity.TweetLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TweetLikeRepository extends JpaRepository<TweetLike, Long> {

    Optional<TweetLike> findByUserIdAndTweet(Long userId, Tweet tweet);


}
