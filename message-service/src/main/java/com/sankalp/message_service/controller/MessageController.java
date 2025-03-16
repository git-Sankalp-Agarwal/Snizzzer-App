package com.sankalp.message_service.controller;

import com.sankalp.message_service.dtos.ChatsDto;
import com.sankalp.message_service.dtos.MessageDeliveredDto;
import com.sankalp.message_service.dtos.MessageDto;
import com.sankalp.message_service.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/readMessage/{messageId}")
    public ChatsDto readMessage(@PathVariable Long messageId){
        return messageService.readMessage(messageId);
    }

    @DeleteMapping("/deleteMessage/{messageId}")
    public ChatsDto deleteMessage(@PathVariable Long messageId){
        return messageService.deleteMessage(messageId);
    }


}
