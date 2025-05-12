package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.user.UpdateUserRequest;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.security.ApiUserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findById(UUID id);

    User save(User user);

    User update(UpdateUserRequest updatedUser, UUID userId);

    List<User> findByLearnToTeach(Skill skill);

    List<User> findByTeachToLearn(Skill skill);

    UserDto getUserFromPrincipal(ApiUserDetails userDetails);
}
