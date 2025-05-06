package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.dtos.SkillDto;
import com.zirom.tutorapi.domain.dtos.UpdateUserRequestDto;
import com.zirom.tutorapi.domain.dtos.UserDto;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.mappers.SkillMapper;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.repositories.UserRepository;
import com.zirom.tutorapi.security.ApiUserDetails;
import com.zirom.tutorapi.services.SkillService;
import com.zirom.tutorapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SkillService skillService;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(UpdateUserRequestDto updatedUser, UUID userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        existingUser.setProfileImage(updatedUser.getProfileImage());
        existingUser.setName(updatedUser.getName());
        existingUser.setUniversityName(updatedUser.getUniversityName());
        existingUser.setDescription(updatedUser.getDescription());
        existingUser.setWantToTeachDetail(updatedUser.getWantToTeachDetail());
        existingUser.setWantToLearnDetail(updatedUser.getWantToLearnDetail());

        UUID newSkillToLearnId = updatedUser.getSkillToLearnId();
        if (!existingUser.getSkillToLearn().getId().equals(newSkillToLearnId)) {
            Skill newSkillToLearn = skillService.findById(newSkillToLearnId);
            existingUser.setSkillToLearn(newSkillToLearn);
        }

        UUID newSkillToTeachId = updatedUser.getSkillToTeachId();
        if (!existingUser.getSkillToTeach().getId().equals(newSkillToTeachId)) {
            Skill newSkillToTeach = skillService.findById(newSkillToTeachId);
            existingUser.setSkillToTeach(newSkillToTeach);
        }

        return userRepository.save(existingUser);
    }

    @Override
    public List<User> findByLearnToTeach(Skill skill) {
        return userRepository.findAllBySkillToTeach(skill);
    }

    @Override
    public List<User> findByTeachToLearn(Skill skill) {
        return userRepository.findAllBySkillToLearn(skill);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserFromPrincipal(ApiUserDetails userDetails) {
        //For some reason I cant pass userDetails.getUser() directly due to LazyLoading issues
        //So I have to reinitialize user object from ID
        UUID userId = userDetails.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        return userMapper.toDto(user);
    }
}
