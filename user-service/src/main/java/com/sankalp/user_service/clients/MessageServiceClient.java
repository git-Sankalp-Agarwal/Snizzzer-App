package com.sankalp.user_service.clients;

import com.sankalp.user_service.dto.ParticipantCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "message-service", path = "/messages")
public interface MessageServiceClient {

    @PostMapping("/participant/createParticipant")
    void createParticipant(@RequestBody ParticipantCreateDto participantCreateDto);

}
