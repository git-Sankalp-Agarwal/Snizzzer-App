package com.sankalp.message_service.dtos;

import com.sankalp.message_service.entity.enums.MessageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class MessageDto {

    private Long messageReceiverId;

    private String messageContent;

    private MessageType messageType;

}
