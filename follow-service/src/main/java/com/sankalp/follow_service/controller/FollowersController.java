package com.sankalp.follow_service.controller;

import com.sankalp.follow_service.entity.Person;
import com.sankalp.follow_service.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class FollowersController {

    private final FollowService followService;

    @GetMapping("/getPerson/{personName}")
    public Person getPersonWithName(@PathVariable String personName){
        return followService.getPersonByName(personName);
    }

    @GetMapping("/getPersonFollowers/{userId}")
    public List<Person> getPersonWithName(@PathVariable Long userId){
        return followService.getFirstFollowers(userId);
    }

}
