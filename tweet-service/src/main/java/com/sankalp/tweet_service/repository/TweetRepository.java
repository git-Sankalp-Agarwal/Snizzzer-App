package com.sankalp.tweet_service.repository;

import com.sankalp.tweet_service.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Optional<List<Tweet>> findByUserId(Long userId);

}
