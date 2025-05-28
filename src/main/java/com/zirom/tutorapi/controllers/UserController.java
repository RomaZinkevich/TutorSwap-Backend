package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.user.UpdateUserRequest;
import com.zirom.tutorapi.domain.dtos.user.UserConnectionDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.mappers.SkillMapper;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.security.ApiUserDetails;
import com.zirom.tutorapi.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/v1/users")
@RequiredArgsConstructor
@Tag(name="User")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final SkillMapper skillMapper;

    @GetMapping(path="/me")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<UserConnectionDto> getUserInfoById(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        UserDto currentUser = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        UserConnectionDto user = userService.findByIdWithConnection(id, currentUser.getId()).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(path="/me")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<UserDto> updateUser(
            Authentication authentication,
            @RequestBody @Valid UpdateUserRequest updatedUserDto
    ) {
        UserDto loggedInUser = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        User updatedUser = userService.update(updatedUserDto, loggedInUser.getId());
        UserDto responseUserDto = userMapper.toDto(updatedUser);
        return new ResponseEntity<>(responseUserDto, HttpStatus.OK);
    }

    @GetMapping()
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<UserDto>> getAllUsers(Authentication authentication) {
        UserDto loggedInUser = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        List<User> users = userService.getAllUsers(loggedInUser.getId());
        List<UserDto> userDtos = users.stream().map(userMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping(path="/learn-to-teach")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<UserDto>> getUsersLearnToTeach(Authentication authentication) {
        UserDto loggedInUser = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        Skill skill = skillMapper.toEntity(loggedInUser.getSkillToLearn());
        List<User> matchedUsers = userService.findByLearnToTeach(skill);
        List<UserDto> matchedUserDtos = matchedUsers.stream().map(userMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(matchedUserDtos, HttpStatus.OK);
    }

    @GetMapping(path="/teach-to-learn")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<UserDto>> getUsersTeachToLearn(Authentication authentication) {
        UserDto loggedInUser = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        Skill skill = skillMapper.toEntity(loggedInUser.getSkillToTeach());
        List<User> matchedUsers = userService.findByTeachToLearn(skill);
        List<UserDto> matchedUserDtos = matchedUsers.stream().map(userMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(matchedUserDtos, HttpStatus.OK);
    }

}