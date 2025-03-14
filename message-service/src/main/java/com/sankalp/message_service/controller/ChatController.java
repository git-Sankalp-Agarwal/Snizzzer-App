package com.sankalp.message_service.controller;

import com.sankalp.message_service.dtos.ChatsDto;
import com.sankalp.message_service.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/readChatWithId/{chatId}")
    public ChatsDto getChatWithId(@PathVariable Long chatId){
        return chatService.getChatWithId(chatId);
    }

}
