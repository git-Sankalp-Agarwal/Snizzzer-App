package com.sankalp.tweet_service.clients;

import com.sankalp.tweet_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "follow-service", path = "/follows")
public interface FollowersClient {

    @GetMapping("/core/getPersonFollowers/{userId}")
    public List<PersonDto> getFollowers(@PathVariable Long userId);

}
