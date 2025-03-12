package com.sankalp.follow_service.services.impl;

import com.sankalp.follow_service.advices.ApiResponse;
import com.sankalp.follow_service.auth.UserContextHolder;
import com.sankalp.follow_service.clients.UsersClient;
import com.sankalp.follow_service.dto.PersonCreateDto;
import com.sankalp.follow_service.dto.PersonDto;
import com.sankalp.follow_service.entity.Person;
import com.sankalp.follow_service.event.UserFollowRequestEvent;
import com.sankalp.follow_service.event.UserFollowedEvent;
import com.sankalp.follow_service.repository.PersonRepository;
import com.sankalp.follow_service.services.FollowService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowServiceImpl implements FollowService {

    private final PersonRepository personRepository;
    private final ModelMapper mapper;
    private final UsersClient usersClient;
    private final KafkaTemplate<Long, UserFollowedEvent> userFollowedEventTemplate;
    private final KafkaTemplate<Long, UserFollowRequestEvent> userFollowRequestEventTemplate;
    private final String FOLLOWERS_CACHE_NAME = "getFollowers";

    @Override
    public Person getPersonByName(String name) {
        return personRepository.findByName(name)
                               .orElseThrow(() -> new RuntimeException("Person not found!!"));
    }

    @Override
    public List<PersonDto> getUserFollowers(Long userId) {
        List<Person> userFollowers = personRepository.findFirstDegreeFollowers(userId);
        return userFollowers.stream()
                            .map(userFollower -> mapper.map(userFollower, PersonDto.class))
                            .toList();
    }

    @Override
    public void createPerson(PersonCreateDto personCreateDto) {

        log.info("Creating person with details ::: {} ", personCreateDto);

        Person person = Person.builder()
                              .userId(personCreateDto.getUserId())
                              .name(personCreateDto.getName())
                              .build();
        personRepository.save(person);
    }

    @Override
    @Transactional
    public void followPerson(Long receiverId) {

        Long senderId = UserContextHolder.getCurrentUserId();
        ApiResponse<Boolean> checkIsPrivateAccount = usersClient.checkAccountPrivacy(receiverId);

        if(checkIsPrivateAccount.getData()){
            sendFollowRequest(senderId, receiverId);
            return;
        }

        if (senderId.equals(receiverId)) {
            throw new RuntimeException("Cannot follow yourself!!!, both sender and receiver are same");
        }
        boolean alreadyFollower = personRepository.isUserFollowing(senderId, receiverId);

        if (alreadyFollower) {
            startUnfollowingUser(senderId, receiverId);
            return;
        }
        String senderName = UserContextHolder.getCurrentUserName();

        personRepository.startFollowing(senderId, receiverId);

        UserFollowedEvent userFollowedEvent = UserFollowedEvent.builder()
                                                               .senderId(senderId)
                                                               .senderName(senderName)
                                                               .receiverId(receiverId)
                                                               .build();

        log.info("Sending user starting following notification to followee: {}", userFollowedEvent);

        userFollowedEventTemplate.send("user-followed-topic", userFollowedEvent);
    }

    private void sendFollowRequest(Long senderId, Long receiverId) {

        String senderName = UserContextHolder.getCurrentUserName();

        boolean alreadyFollower = personRepository.isUserFollowing(senderId, receiverId);

        if (alreadyFollower) {
            throw new RuntimeException("Do you want to Unfollow??");
        }

        boolean checkFollowRequestExist = personRepository.doesUserFollowRequestExists(senderId, receiverId);

        if(checkFollowRequestExist){
            throw new RuntimeException("Follow request already exist!!!! Do you want to cancel your request??");
        }

        personRepository.sendFollowRequest(senderId, receiverId);

        UserFollowRequestEvent userFollowRequestEvent = UserFollowRequestEvent.builder()
                                                                    .senderId(senderId)
                                                                    .senderName(senderName)
                                                                    .receiverId(receiverId)
                                                                    .build();

        log.info("Sending user send a follow request notification to followee: {}", userFollowRequestEvent);

//        userFollowRequestEventTemplate.send("user-follow-request-topic", userFollowRequestEvent);
    }

    @Override
    public List<PersonDto> getMyFollowers() {
        Long userId = UserContextHolder.getCurrentUserId();

        List<Person> userFollowers = personRepository.findFirstDegreeFollowers(userId);
        return userFollowers.stream()
                            .map(userFollower -> mapper.map(userFollower, PersonDto.class))
                            .toList();
    }

    @Override
    public boolean checkIfUserIsFollower(Long senderId, Long receiverId) {

        return personRepository.isUserFollowing(senderId, receiverId);

     //   return new ApiResponse<>(checkIfUserIsFollower);

    }


    public void startUnfollowingUser(Long senderId, Long receiverId) {
        personRepository.startUnFollowing(senderId, receiverId);
    }
}