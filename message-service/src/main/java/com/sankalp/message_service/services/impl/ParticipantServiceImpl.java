package com.sankalp.message_service.services.impl;

import com.sankalp.message_service.dtos.ParticipantCreateDto;
import com.sankalp.message_service.entity.Participant;
import com.sankalp.message_service.exceptions.ResourceNotFoundException;
import com.sankalp.message_service.repository.ParticipantRepository;
import com.sankalp.message_service.services.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    @Override
    public void createNewParticipant(ParticipantCreateDto participantCreateDto) {
        Participant participant = Participant.builder()
                                             .participantUserId(participantCreateDto.getParticipantUserId())
                                             .participantName(participantCreateDto.getParticipantName())
                                             .build();
        participantRepository.save(participant);

    }

    @Override
    public Participant fetchParticipantByUserId(Long userId) {
        return participantRepository.findByParticipantUserId(userId)
                                    .orElseThrow(() -> new ResourceNotFoundException("No participant found"));
    }
}
