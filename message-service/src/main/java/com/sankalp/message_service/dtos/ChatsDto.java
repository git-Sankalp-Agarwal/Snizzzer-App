package com.sankalp.message_service.dtos;

import com.sankalp.message_service.entity.Participant;
import lombok.Data;

import java.util.List;

@Data
public class ChatsDto {

    private Participant participantOne;

    private Participant participantTwo;

    private List<MessageSentDto> messages;

}
