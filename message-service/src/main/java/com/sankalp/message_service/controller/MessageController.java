package com.sankalp.message_service.controller;

import com.sankalp.message_service.annotations.RolesAllowed;
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

    @RolesAllowed({"ADMIN", "PREMIUM"})
    @PostMapping("/sendMessage")
    public ChatsDto sendMessage(@RequestBody MessageDto messageDto){
        return messageService.sendMessage(messageDto);
    }

    @PostMapping("/updateMessageAndChatStatus")
    void updateMessageStatus(@RequestBody MessageDeliveredDto messageDeliveredDto){
        messageService.updateMessageStatusToDelivered(messageDeliveredDto);
    }

    @RolesAllowed({"ADMIN", "PREMIUM"})
    @GetMapping("/readMessage/{messageId}")
    public ChatsDto readMessage(@PathVariable Long messageId){
        return messageService.readMessage(messageId);
    }

    @RolesAllowed({"ADMIN", "PREMIUM"})
    @DeleteMapping("/deleteMessage/{messageId}")
    public ChatsDto deleteMessage(@PathVariable Long messageId){
        return messageService.deleteMessage(messageId);
    }


}
