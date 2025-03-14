package com.sankalp.message_service.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendEvent {

    private Long senderId;

    private Long receiverId;

    private Long messageId;

    private Long chatsId;

    private String messageSender;

    private String messageContent;


}
