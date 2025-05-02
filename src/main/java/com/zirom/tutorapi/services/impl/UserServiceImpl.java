package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.repositories.UserRepository;
import com.zirom.tutorapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByGoogleSub(String googleSub) {
        return userRepository.findById(googleSub);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
