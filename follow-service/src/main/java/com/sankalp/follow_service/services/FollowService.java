package com.sankalp.follow_service.services;

import com.sankalp.follow_service.dto.PersonCreateDto;
import com.sankalp.follow_service.entity.Person;

import java.util.List;

public interface FollowService {

    Person getPersonByName(String name);

    List<Person> getFirstFollowers(Long userId);

    void createPerson(PersonCreateDto personCreateDto);
}
