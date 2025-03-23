package com.sankalp.follow_service.controller;

import com.sankalp.follow_service.advices.ApiResponse;
import com.sankalp.follow_service.annotations.RolesAllowed;
import com.sankalp.follow_service.dto.PersonCreateDto;
import com.sankalp.follow_service.dto.PersonDto;
import com.sankalp.follow_service.entity.Person;
import com.sankalp.follow_service.services.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class FollowersController {

    private final FollowService followService;

    @GetMapping("/getPerson/{personName}")
    public Person getPersonWithName(@PathVariable String personName){

        log.info("Getting person with personName::: {} ", personName);
        return followService.getPersonByName(personName);
    }

    @GetMapping("/getPersonFollowers/{userId}")
    public List<PersonDto> getFollowers(@PathVariable Long userId){
        log.info("Getting first degree followers for userId::: {} ", userId);
        return followService.getUserFollowers(userId);
    }

    @GetMapping("/IsFollower/sender/{senderId}/receiver/{receiverId}")
    public boolean checkIfUserIsFollower(@PathVariable Long senderId , @PathVariable Long receiverId){
        log.info("Checking if userId::: {} is a followers for userId::: {} ", senderId, receiverId);
        return followService.checkIfUserIsFollower(senderId, receiverId);
    }

    @RolesAllowed({"USER"})
    @PostMapping("/startFollowing/{receiverId}")
    public void startFollowing(@PathVariable Long receiverId){
        followService.followPerson(receiverId);
    }

    @RolesAllowed({"USER"})
    @PostMapping("/acceptFollowRequest/{senderId}")
    public void acceptFollowRequest(@PathVariable Long senderId){
        followService.acceptFollowRequest(senderId);
    }

    @RolesAllowed({"USER"})
    @PostMapping("/acceptFollowRequest/{senderId}")
    public void rejectFollowRequest(@PathVariable Long senderId){
        followService.rejectFollowRequest(senderId);
    }

    @GetMapping("/getMyFollowers")
    public List<PersonDto> getMyFollowers(){
        return followService.getMyFollowers();
    }

    @PostMapping("/createPerson")
    public void createPerson(@RequestBody PersonCreateDto personCreateDto){

        followService.createPerson(personCreateDto);
    }

}
