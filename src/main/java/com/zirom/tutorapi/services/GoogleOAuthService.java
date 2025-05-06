package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.SignUpRequestDto;
import com.zirom.tutorapi.domain.dtos.UserDto;
import com.zirom.tutorapi.domain.entities.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface GoogleOAuthService {

    Optional<User> googleAuthenticate(String idToken);

    User signup(SignUpRequestDto dto);

    String generateToken(UserDto userDto);

    UserDetails validateToken(String token);
}
