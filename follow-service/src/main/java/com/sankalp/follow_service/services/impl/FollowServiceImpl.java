package com.sankalp.follow_service.services.impl;

import com.sankalp.follow_service.entity.Person;
import com.sankalp.follow_service.repository.PersonRepository;
import com.sankalp.follow_service.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final PersonRepository personRepository;

    @Override
    public Person getPersonByName(String name) {
        return personRepository.findByName(name).orElseThrow(() -> new RuntimeException("Person not found!!"));
    }

    @Override
    public List<Person> getFirstFollowers(Long userId) {
        return personRepository.findFirstDegreeFollowers(userId);
    }
}