package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.user.UpdateUserRequest;
import com.zirom.tutorapi.domain.dtos.user.UserConnectionDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.security.ApiUserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> getAllUsers(UUID userId);
    Optional<User> findById(UUID id);
    Optional<UserConnectionDto> findByIdWithConnection(UUID targetUserId, UUID currentUserId);

    User save(User user);

    User update(UpdateUserRequest updatedUser, UUID userId);

    List<User> findByLearnToTeach(Skill skill);

    List<User> findByTeachToLearn(Skill skill);

    UserDto getUserFromPrincipal(ApiUserDetails userDetails);
}
