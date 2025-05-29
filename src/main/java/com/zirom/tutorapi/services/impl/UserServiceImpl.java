package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.ConnectionType;
import com.zirom.tutorapi.domain.dtos.user.UpdateUserRequest;
import com.zirom.tutorapi.domain.dtos.user.UserConnectionDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
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
        Map<String, Object> result = userRepository.rawUserConnection(currentUserId, ta.rgetUserId);
        System.out.println(result.get("id"));
        System.out.println(targetUserId);
        System.out.println(currentUserId);

        Skill skillToLearn = skillService.findById((UUID) result.get("skill_to_learn_id"));
        Skill skillToTeach = skillService.findById((UUID) result.get("skill_to_teach_id"));
        return Optional.ofNullable(UserConnectionDto.builder()
                .id((UUID) result.get("id"))
                .name((String) result.get("name"))
                .email((String) result.get("email"))
                .profileImage((String) result.get("profile_image"))
                .description((String) result.get("description"))
                .universityName((String) result.get("university_name"))
                .wantToLearnDetail((String) result.get("want_to_learn_detail"))
                .wantToTeachDetail((String) result.get("want_to_teach_detail"))
                .accepted(result.get("accepted") != null && (boolean) result.get("accepted"))
                .connectionType(result.get("connection_type") != null ? ConnectionType.valueOf(result.get("connection_type").toString()) : null)
                .isSender(result.get("is_sender") != null && (boolean) result.get("is_sender"))
                .skillToLearn(skillMapper.toDto(skillToLearn))
                .skillToTeach(skillMapper.toDto(skillToTeach))
                .build());
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
