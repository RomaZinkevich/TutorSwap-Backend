package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.connection.ConnectionDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.Connection;
import com.zirom.tutorapi.mappers.ConnectionMapper;
import com.zirom.tutorapi.security.ApiUserDetails;
import com.zirom.tutorapi.services.ConnectionService;
import com.zirom.tutorapi.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/connections")
@RequiredArgsConstructor
@Tag(name="Connection")
public class ConnectionContoller {

    private final ConnectionService connectionService;
    private final UserService userService;
    private final ConnectionMapper connectionMapper;

    @GetMapping
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<ConnectionDto>> getConnections(Authentication authentication) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        List<Connection> connections = connectionService.getAllConnectionsByUserId(user.getId());
        List<ConnectionDto> connectionDtos = connections.stream().map(connectionMapper::toDto).toList();
        return new ResponseEntity<>(connectionDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity deleteConnection(
            Authentication authentication,
            @PathVariable UUID userId
    ) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        connectionService.deleteConnection(userId, user.getId());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
