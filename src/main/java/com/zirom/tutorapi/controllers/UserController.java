package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.SkillDto;
import com.zirom.tutorapi.domain.dtos.UpdateUserRequestDto;
import com.zirom.tutorapi.domain.dtos.UserDto;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.mappers.SkillMapper;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final SkillMapper skillMapper;

    @GetMapping
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<UserDto> getUserInfoById(@PathVariable UUID id) {
        User user = userService.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));;
        UserDto userDto = userMapper.toDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(
            Authentication authentication,
            @RequestBody UpdateUserRequestDto updatedUserDto
    ) {
        UserDto loggedInUser = (UserDto) authentication.getPrincipal();
        User updatedUser = userService.update(updatedUserDto, loggedInUser.getId());
        UserDto responseUserDto = userMapper.toDto(updatedUser);
        return new ResponseEntity<>(responseUserDto, HttpStatus.OK);
    }

    @GetMapping(path="/learn-to-teach")
    public ResponseEntity<List<UserDto>> getUsersLearnToTeach(Authentication authentication) {
        UserDto loggedInUser = (UserDto) authentication.getPrincipal();
        Skill skill = skillMapper.toEntity(loggedInUser.getSkillToLearn());
        List<User> matchedUsers = userService.findByLearnToTeach(skill);
        List<UserDto> matchedUserDtos = matchedUsers.stream().map(userMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(matchedUserDtos, HttpStatus.OK);
    }

    @GetMapping(path="/teach-to-learn")
    public ResponseEntity<List<UserDto>> getUsersTeachToLearn(Authentication authentication) {
        UserDto loggedInUser = (UserDto) authentication.getPrincipal();
        Skill skill = skillMapper.toEntity(loggedInUser.getSkillToTeach());
        List<User> matchedUsers = userService.findByTeachToLearn(skill);
        List<UserDto> matchedUserDtos = matchedUsers.stream().map(userMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(matchedUserDtos, HttpStatus.OK);
    }

}