package com.sankalp.notification_service.repository;

import com.sankalp.notification_service.entity.FollowNotifications;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FollowNotificationsRepository extends MongoRepository<FollowNotifications, Long> {
}
