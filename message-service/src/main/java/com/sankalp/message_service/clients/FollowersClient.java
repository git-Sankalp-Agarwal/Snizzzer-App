package com.sankalp.message_service.clients;


import com.sankalp.message_service.advices.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "follow-service", path = "/follows")
public interface FollowersClient {

    @GetMapping("/core/IsFollower/sender/{senderId}/receiver/{receiverId}")
    ApiResponse<Boolean> checkIfUserIsFollower(@PathVariable Long senderId , @PathVariable Long receiverId);

}
