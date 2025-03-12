package com.sankalp.tweet_service.clients;

import com.sankalp.tweet_service.advices.ApiResponse;
import com.sankalp.tweet_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "follow-service", path = "/follows")
public interface FollowersClient {

    @GetMapping("/core/IsFollower/sender/{senderId}/receiver/{receiverId}")
    ApiResponse<Boolean> checkIfUserIsFollower(@PathVariable Long senderId , @PathVariable Long receiverId);

}
