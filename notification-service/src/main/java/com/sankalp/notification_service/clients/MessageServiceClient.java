package com.sankalp.notification_service.clients;


import com.sankalp.notification_service.dto.MessageDeliveredDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "message-service", path = "/messages")
public interface MessageServiceClient {

    @PostMapping("/core/updateMessageAndChatStatus")
    void updateMessageStatus(@RequestBody MessageDeliveredDto messageDeliveredDto);

}
