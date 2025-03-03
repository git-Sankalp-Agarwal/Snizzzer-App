package com.sankalp.notification_service.entity;

import com.sankalp.notification_service.entity.enums.NotificationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Follow_Notifications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowNotifications {

    @Id
    private String id;

    private Long senderId;

    private Long receiverId;

    private String message;

    private NotificationType notificationType;


}
