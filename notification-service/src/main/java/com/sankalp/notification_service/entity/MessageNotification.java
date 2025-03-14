package com.sankalp.notification_service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Message_Notifications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageNotification {

    @Id
    private String id;

    private Long senderId;

    private Long receiverId;

    private Long messageId;

    private Long chatsId;

    private String messageSender;

    private String messageContent;

}
