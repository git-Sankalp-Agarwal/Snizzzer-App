package com.sankalp.follow_service.services.impl;

import com.sankalp.follow_service.auth.UserContextHolder;
import com.sankalp.follow_service.dto.PersonCreateDto;
import com.sankalp.follow_service.entity.Person;
import com.sankalp.follow_service.repository.PersonRepository;
import com.sankalp.follow_service.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final PersonRepository personRepository;
    private final ModelMapper mapper;

    @Override
    public Person getPersonByName(String name) {
        return personRepository.findByName(name)
                               .orElseThrow(() -> new RuntimeException("Person not found!!"));
    }

    @Override
    public List<Person> getFirstFollowers(Long userId) {
        return personRepository.findFirstDegreeFollowers(userId);
    }

    @Override
    public void createPerson(PersonCreateDto personCreateDto) {
        Person person = Person.builder()
                              .userId(personCreateDto.getUserId())
                              .name(personCreateDto.getName())
                              .build();
        personRepository.save(person);
    }

    @Override
    public void sendNotificationToFollowers() {
        Long userId = UserContextHolder.getCurrentUserId();

        List<Person> followers = personRepository.findFirstDegreeFollowers(userId);

    }
}