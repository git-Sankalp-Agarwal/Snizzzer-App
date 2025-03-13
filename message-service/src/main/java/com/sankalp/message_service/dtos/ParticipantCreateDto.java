package com.sankalp.message_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantCreateDto {

    private Long participantUserId;

    private String participantName;

}
