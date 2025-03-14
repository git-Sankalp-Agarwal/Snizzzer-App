package com.sankalp.notification_service.dto;

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
