package com.sankalp.follow_service.controller;

import com.sankalp.follow_service.dto.PersonCreateDto;
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
    public List<Person> getFollowers(@PathVariable Long userId){
        log.info("Getting first degree followers for userId::: {} ", userId);
        return followService.getFirstFollowers(userId);
    }

    @PostMapping("/createPerson")
    public void createPerson(@RequestBody PersonCreateDto personCreateDto){

        log.info("Creating person with details ::: {} ", personCreateDto);

        followService.createPerson(personCreateDto);
    }

}
