package com.sankalp.message_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDeliveredDto {

    private Long messageId;

    private Long chatsId;

}
