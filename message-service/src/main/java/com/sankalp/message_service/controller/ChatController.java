package com.sankalp.message_service.controller;

import com.sankalp.message_service.annotations.RolesAllowed;
import com.sankalp.message_service.dtos.ChatsDto;
import com.sankalp.message_service.services.ChatService;
import com.sankalp.message_service.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;

    @GetMapping("/readChatWithId/{chatId}")
    public ChatsDto getChatWithIdForUser(@PathVariable Long chatId){
        return chatService.getChatWithId(chatId);
    }
    @RolesAllowed({"ADMIN", "PREMIUM"})
    @GetMapping("/readChat/{chatId}")
    public ChatsDto getUserChat(@PathVariable Long chatId){
        return messageService.readAndUpdateChat(chatId);
    }


}
