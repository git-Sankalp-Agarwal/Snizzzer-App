package com.sankalp.message_service.event;

import com.sankalp.message_service.entity.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSendEvent {

    private Long senderId;

    private Long receiverId;

    private Long messageId;

    private Long chatsId;

    private String messageSender;

    private String messageContent;

}
