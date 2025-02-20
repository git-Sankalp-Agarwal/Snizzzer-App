package com.sankalp.tweet_service.repository;

import com.sankalp.tweet_service.entity.Tweet;
import com.sankalp.tweet_service.entity.TweetLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetLikeRepository extends JpaRepository<TweetLike, Long> {

    public boolean existsByUserIdAndTweet(Long userId, Tweet tweet);

    public void deleteByUserIdAndTweet(Long userId, Tweet tweet);

}
