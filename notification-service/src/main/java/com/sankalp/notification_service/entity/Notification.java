package com.sankalp.notification_service.entity;

import com.sankalp.notification_service.entity.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long tweetId;

    private String message;

    @Enumerated(value = EnumType.STRING)
    private NotificationType notificationType;


}
