package com.sankalp.tweet_service.repository;

import com.sankalp.tweet_service.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
}
