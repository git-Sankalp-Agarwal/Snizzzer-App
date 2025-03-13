package com.sankalp.message_service.services;

import com.sankalp.message_service.dtos.ParticipantCreateDto;
import com.sankalp.message_service.entity.Participant;

public interface ParticipantService {

    void createNewParticipant(ParticipantCreateDto participantCreateDto);

    Participant fetchParticipantByUserId(Long userId);

}
