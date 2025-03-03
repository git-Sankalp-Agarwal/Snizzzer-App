package com.sankalp.follow_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowedEvent {

    private Long senderId;

    private Long receiverId;

    private String senderName;

}
