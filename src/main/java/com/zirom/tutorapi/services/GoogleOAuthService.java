package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.SignUpRequestDto;
import com.zirom.tutorapi.domain.dtos.UserDto;
import com.zirom.tutorapi.domain.entities.User;

import java.util.Optional;

public interface GoogleOAuthService {

    Optional<User> authenticate(String idToken);

    User signup(SignUpRequestDto dto);

    String generateToken(UserDto userDto);

    UserDto validateToken(String token);
}
