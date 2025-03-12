package com.sankalp.tweet_service.clients;

import com.sankalp.tweet_service.advices.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/users")
public interface UsersClient {

    @GetMapping("/auth/checkAccountPrivacy/{userId}")
    ApiResponse<Boolean> checkAccountPrivacy(@PathVariable Long userId);

}
