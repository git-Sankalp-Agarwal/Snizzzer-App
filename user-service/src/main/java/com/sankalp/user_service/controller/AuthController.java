package com.sankalp.user_service.controller;



import com.sankalp.user_service.dto.LoginRequestDto;
import com.sankalp.user_service.dto.LoginResponseDto;
import com.sankalp.user_service.dto.SignupRequestDto;
import com.sankalp.user_service.dto.UserDto;
import com.sankalp.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) {
        UserDto userDto = authService.signUp(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = authService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(token));
    }

    @PostMapping("/changeAccountPrivacy")
    public ResponseEntity<UserDto> changeAccountPrivacy(){
        return ResponseEntity.status(HttpStatus.OK).body(authService.changeUserPrivacy());
    }

    @GetMapping("/checkAccountPrivacy/{userId}")
    public boolean checkAccountPrivacy(@PathVariable Long userId){
        return authService.checkUserPrivacy(userId);
    }

    @PostMapping("/uploadProfileImage")
    public ResponseEntity<UserDto> uploadProfileImage(@RequestParam("image") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(authService.uploadUserProfileImage(file));
    }
}
