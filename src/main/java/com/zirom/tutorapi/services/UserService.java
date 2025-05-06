package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.SkillDto;
import com.zirom.tutorapi.domain.dtos.UpdateUserRequestDto;
import com.zirom.tutorapi.domain.dtos.UserDto;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.security.ApiUserDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findById(UUID id);

    User save(User user);

    User update(UpdateUserRequestDto updatedUser, UUID userId);

    List<User> findByLearnToTeach(Skill skill);

    List<User> findByTeachToLearn(Skill skill);

    UserDto getUserFromPrincipal(ApiUserDetails userDetails);
}
