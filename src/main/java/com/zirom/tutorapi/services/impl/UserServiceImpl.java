package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.ConnectionType;
import com.zirom.tutorapi.domain.dtos.user.UpdateUserRequest;
import com.zirom.tutorapi.domain.dtos.user.UserConnectionDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.mappers.SkillMapper;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.repositories.ConnectionRequestRepository;
import com.zirom.tutorapi.repositories.UserRepository;
import com.zirom.tutorapi.security.ApiUserDetails;
import com.zirom.tutorapi.services.SkillService;
import com.zirom.tutorapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SkillService skillService;
    private final UserMapper userMapper;
    private final SkillMapper skillMapper;
    private final ConnectionRequestRepository connectionRequestRepository;

    @Override
    public List<User> getAllUsers(UUID userId) {
        return userRepository.findAllByIdIsNot(userId);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<UserConnectionDto> findByIdWithConnection(UUID targetUserId, UUID currentUserId) {
        User targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Optional<ConnectionRequest> connectionBetween = connectionRequestRepository.findConnectionBetween(currentUserId, targetUserId);
        Skill skillToLearn = targetUser.getSkillToLearn();
        Skill skillToTeach = targetUser.getSkillToTeach();
        UserConnectionDto userConnectionDto = new UserConnectionDto();
        userConnectionDto.setId(targetUserId);
        userConnectionDto.setName(targetUser.getName());
        userConnectionDto.setEmail(targetUser.getEmail());
        userConnectionDto.setProfileImage(targetUser.getProfileImage());
        userConnectionDto.setDescription(targetUser.getDescription());
        userConnectionDto.setUniversityName(targetUser.getUniversityName());
        userConnectionDto.setWantToLearnDetail(targetUser.getWantToLearnDetail());
        userConnectionDto.setWantToTeachDetail(targetUser.getWantToTeachDetail());
        userConnectionDto.setSkillToLearn(skillMapper.toDto(targetUser.getSkillToLearn()));
        userConnectionDto.setSkillToTeach(skillMapper.toDto(targetUser.getSkillToTeach()));

        if (connectionBetween.isPresent()) {
            ConnectionRequest connectionRequest = connectionBetween.get();
            userConnectionDto.setAccepted(connectionRequest.isAccepted());
            userConnectionDto.setConnectionType(connectionRequest.getConnectionType());
            userConnectionDto.setSender(connectionRequest.getSenderUser().getId() == targetUserId);
            userConnectionDto.setConnectionRequestExists(true);
        } else {
            userConnectionDto.setConnectionRequestExists(false);
        }
        return Optional.of(userConnectionDto);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(UpdateUserRequest updatedUser, UUID userId) {
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
