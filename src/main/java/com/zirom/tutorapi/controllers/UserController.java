package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path="/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}