package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.user.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    UserDetails validateToken(String token);

    String extractId(String token);

    String generateToken(UserDto userDto);
}
