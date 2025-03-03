package com.sankalp.notification_service.repository;


import com.sankalp.notification_service.entity.Notification;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, Long> {
}
