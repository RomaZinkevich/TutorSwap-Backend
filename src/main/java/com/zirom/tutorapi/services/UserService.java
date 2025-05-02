package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.entities.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByGoogleSub(String googleSub);
    User save(User user);
}
