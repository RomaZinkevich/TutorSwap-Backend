package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.connection.CreateConnectionRequest;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;

import java.util.List;
import java.util.UUID;

public interface ConnectionRequestService {
    List<ConnectionRequest> getConnectionsByUser(UUID receiverId, boolean isAccepted);

    ConnectionRequest addConnectionRequest(UserDto user, CreateConnectionRequest createConnectionRequest);

    ConnectionRequest updateAccepted(UUID id, UserDto loggedinUserDto);
}
