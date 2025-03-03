package com.sankalp.user_service.clients;


import com.sankalp.user_service.dto.PersonCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "follow-service", path = "/follows")
public interface FollowersClient {

    @PostMapping("/core/createPerson")
    void createPerson(@RequestBody PersonCreateDto personCreateDto);

}
