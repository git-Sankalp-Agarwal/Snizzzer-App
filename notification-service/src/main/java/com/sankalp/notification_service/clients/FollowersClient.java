package com.sankalp.notification_service.clients;

import com.sankalp.notification_service.advices.ApiResponse;
import com.sankalp.notification_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "follow-service", path = "/follows")
public interface FollowersClient {

    @GetMapping("/core/getMyFollowers")
    ApiResponse<List<PersonDto>> getMyFollowers(@RequestHeader(name = "X-User-Id", required = false) Long userId, @RequestHeader(name = "X-User-Name", required = false) String postCreatorName);

}
