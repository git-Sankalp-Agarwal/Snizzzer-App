package com.sankalp.follow_service.services;

import com.sankalp.follow_service.advices.ApiResponse;
import com.sankalp.follow_service.dto.PersonCreateDto;
import com.sankalp.follow_service.dto.PersonDto;
import com.sankalp.follow_service.entity.Person;

import java.util.List;

public interface FollowService {

    Person getPersonByName(String name);

    List<PersonDto> getUserFollowers(Long userId);

    void createPerson(PersonCreateDto personCreateDto);

    void followPerson(Long receiverId);

    List<PersonDto> getMyFollowers();

    boolean checkIfUserIsFollower(Long senderId, Long receiverId);

    void acceptFollowRequest(Long followRequestSenderId);

    void rejectFollowRequest(Long followRequestSenderId);


}
