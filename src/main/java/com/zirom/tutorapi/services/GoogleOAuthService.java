package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.auth.SignUpRequest;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface GoogleOAuthService {

    Optional<User> googleAuthenticate(String idToken);

    User signup(SignUpRequest dto);
}
