package com.sankalp.message_service.controller;

import com.sankalp.message_service.dtos.ChatsDto;
import com.sankalp.message_service.dtos.MessageDeliveredDto;
import com.sankalp.message_service.dtos.MessageDto;
import com.sankalp.message_service.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/sendMessage")
    public ChatsDto sendMessage(@RequestBody MessageDto messageDto){
        return messageService.sendMessage(messageDto);
    }

    @PostMapping("/updateMessageAndChatStatus")
    void updateMessageStatus(@RequestBody MessageDeliveredDto messageDeliveredDto){
        messageService.updateMessageStatusToDelivered(messageDeliveredDto);
    }


}
