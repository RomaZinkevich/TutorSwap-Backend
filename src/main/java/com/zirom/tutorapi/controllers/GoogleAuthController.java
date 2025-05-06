package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.*;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.services.GoogleOAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1/auth/google")
@RequiredArgsConstructor
public class GoogleAuthController {

    private final GoogleOAuthService googleOAuthService;
    private final UserMapper userMapper;

    @PostMapping()
    public ResponseEntity<GoogleAuthUserResponse> googleAuth(@RequestBody @Valid GoogleAuthRequestDto request) {
        Optional<User> authenticateUser = googleOAuthService.googleAuthenticate(request.getIdToken());
        GoogleAuthUserResponse response = new GoogleAuthUserResponse();
        if (authenticateUser.isPresent()) {
            User user = authenticateUser.get();
            UserDto userDto = userMapper.toDto(user);
            String token = googleOAuthService.generateToken(userDto);
            response.setMessage("User found");
            response.setNewUser(false);
            response.setToken(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("User not found");
            response.setNewUser(true);
            response.setToken(null);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<String> registerUser(@RequestBody @Valid SignUpRequestDto request) {
        User newUser = googleOAuthService.signup(request);
        UserDto userDto = userMapper.toDto(newUser);
        String token = googleOAuthService.generateToken(userDto);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}
