package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.connection.ConnectionRequestDto;
import com.zirom.tutorapi.domain.dtos.connection.CreateConnectionRequest;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.mappers.ConnectionRequestMapper;
import com.zirom.tutorapi.security.ApiUserDetails;
import com.zirom.tutorapi.services.ConnectionRequestService;
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
@RequestMapping(path="/api/v1/connection-requests")
@RequiredArgsConstructor
@Tag(name="Connection Request")
public class ConnectionRequestController {

    private final UserService userService;
    private final ConnectionRequestService connectionRequestService;
    private final ConnectionRequestMapper connectionRequestMapper;

    @GetMapping
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<ConnectionRequestDto>> getPrivatePendingConnectionRequests(Authentication authentication) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        List<com.zirom.tutorapi.domain.entities.ConnectionRequest> connectionRequests = connectionRequestService.getConnectionsByUser(user.getId(), false);
        List<ConnectionRequestDto> connectionRequestDtoDtos = connectionRequests.stream().map(connectionRequestMapper::toDto).toList();
        return new ResponseEntity<>(connectionRequestDtoDtos, HttpStatus.OK);
    }

    @PostMapping
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<ConnectionRequestDto> createConnectionRequest(
            @RequestBody CreateConnectionRequest createConnectionRequest,
            Authentication authentication
    ) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        com.zirom.tutorapi.domain.entities.ConnectionRequest connectionRequest = connectionRequestService.addConnectionRequest(user, createConnectionRequest);
        ConnectionRequestDto connectionRequestDto = connectionRequestMapper.toDto(connectionRequest);
        return new ResponseEntity<>(connectionRequestDto, HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<ConnectionRequestDto> updateConnectionRequest(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        com.zirom.tutorapi.domain.entities.ConnectionRequest connectionRequest = connectionRequestService.updateAccepted(id, user);
        ConnectionRequestDto connectionRequestDto = connectionRequestMapper.toDto(connectionRequest);
        return new ResponseEntity<>(connectionRequestDto, HttpStatus.OK);
    }
}
