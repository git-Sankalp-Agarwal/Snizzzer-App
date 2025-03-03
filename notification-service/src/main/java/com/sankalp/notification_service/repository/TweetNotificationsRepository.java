package com.sankalp.notification_service.repository;




import com.sankalp.notification_service.entity.TweetNotifications;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetNotificationsRepository extends MongoRepository<TweetNotifications, Long> {
}
