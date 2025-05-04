package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.SkillDto;
import com.zirom.tutorapi.domain.dtos.UpdateUserRequestDto;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findByGoogleSub(String googleSub);
    User save(User user);

    User update(UpdateUserRequestDto updatedUser, UUID userId);

    List<User> findByLearnToTeach(Skill skill);

    List<User> findByTeachToLearn(Skill skill);
}
