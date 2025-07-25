package com.sankalp.user_service.service;


import com.sankalp.user_service.advices.ApiResponse;
import com.sankalp.user_service.auth.UserContextHolder;
import com.sankalp.user_service.clients.FollowersClient;
import com.sankalp.user_service.clients.MessageServiceClient;
import com.sankalp.user_service.clients.UploadServiceClient;
import com.sankalp.user_service.dto.*;
import com.sankalp.user_service.entity.User;
import com.sankalp.user_service.entity.enums.AccountType;
import com.sankalp.user_service.entity.enums.Role;
import com.sankalp.user_service.exception.BadRequestException;
import com.sankalp.user_service.exception.ResourceNotFoundException;
import com.sankalp.user_service.repository.UserRepository;
import com.sankalp.user_service.utils.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final FollowersClient followersClient;
    private final MessageServiceClient messageServiceClient;
    private final UploadServiceClient uploadServiceClient;

    @Transactional
    public UserDto signUp(SignupRequestDto signupRequestDto) {
        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if (exists) {
            throw new BadRequestException("User already exists, cannot signup again.");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setAccountType(AccountType.PUBLIC);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        user.setRoles(Set.of(Role.USER));
        log.info("Saving user: {}", user);
        User savedUser = userRepository.save(user);

        createPerson(savedUser); // create person for follower service

        createParticipant(savedUser); // create participant for message service

        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.getByEmail(loginRequestDto.getEmail())
                                  .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginRequestDto.getEmail()));

        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDto.getPassword(), user.getPassword());

        if (!isPasswordMatch) {
            throw new BadRequestException("Incorrect password");
        }

        return jwtService.generateAccessToken(user);
    }

    public void createParticipant(User user) {
        log.info("Creating participant for message service");

        ParticipantCreateDto participantCreateDto = ParticipantCreateDto.builder()
                                                                        .participantUserId(user.getId())
                                                                        .participantName(user.getFirstName())
                                                                        .build();
        messageServiceClient.createParticipant(participantCreateDto);
    }


    public void createPerson(User user) {
        log.info("Creating person for followers service");

        PersonCreateDto person = new PersonCreateDto();
        person.setUserId(user.getId());
        person.setName(user.getFirstName());

        followersClient.createPerson(person);
    }

    public UserDto changeUserPrivacy() {
        Long userId = UserContextHolder.getCurrentUserId();

        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("user not found"));

        log.info("Changing User privacy for user :: {}", user);

        if (user.getAccountType()
                .equals(AccountType.PUBLIC)) { // changing user privacy data
            user.setAccountType(AccountType.PRIVATE);
        } else {
            user.setAccountType(AccountType.PUBLIC);
        }

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    public boolean checkUserPrivacy(Long userId) {

        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("user not found"));

        return user.getAccountType()
                   .equals(AccountType.PRIVATE);

        //  return new ApiResponse<>(isPrivate);
    }

    public UserDto uploadUserProfileImage(MultipartFile file) {
        Long userId = UserContextHolder.getCurrentUserId();

        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("user not found"));

        log.info("Uploading profile image for user :: {}", user);

        String imageUrl = uploadServiceClient.uploadImage(file);
        user.setImageUrl(imageUrl);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }
}
