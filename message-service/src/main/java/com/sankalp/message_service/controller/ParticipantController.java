package com.sankalp.message_service.controller;

import com.sankalp.message_service.dtos.ParticipantCreateDto;
import com.sankalp.message_service.services.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/createParticipant")
    public void createParticipant(@RequestBody ParticipantCreateDto participantCreateDto){
        participantService.createNewParticipant(participantCreateDto);
    }

}
