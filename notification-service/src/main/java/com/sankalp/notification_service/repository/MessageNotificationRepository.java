package com.sankalp.notification_service.repository;

import com.sankalp.notification_service.entity.MessageNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageNotificationRepository extends MongoRepository<MessageNotification, String> {
}
