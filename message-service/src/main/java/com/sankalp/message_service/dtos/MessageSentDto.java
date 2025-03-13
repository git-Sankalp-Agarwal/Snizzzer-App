package com.sankalp.message_service.dtos;

import com.sankalp.message_service.entity.enums.MessageStatus;
import com.sankalp.message_service.entity.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSentDto {

    private String messageContent;

    private MessageType messageType;

    private MessageStatus messageStatus;

    private LocalDateTime messageCreationTime;

    private LocalDateTime editedAt;

}
