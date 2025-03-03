package com.sankalp.notification_service.entity;

import com.sankalp.notification_service.entity.enums.NotificationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    private String id;

    private Long userId;

    private Long tweetId;

    private String message;

    private NotificationType notificationType;


}
