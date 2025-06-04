package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.ConnectionRequestDirection;
import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.dtos.connection.CreateConnectionRequest;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;

import java.util.List;
import java.util.UUID;

public interface ConnectionRequestService {
    List<ConnectionRequest> getConnectionsByUser(UUID receiverId, RequestState state, ConnectionRequestDirection direction);

    ConnectionRequest addConnectionRequest(UserDto user, CreateConnectionRequest createConnectionRequest);

    ConnectionRequest updateConnectionRequest(UUID targetId, UUID loggedInUserId, boolean isAccepted);

    void deleteConnectionRequest(UUID targetId, UUID loggedInUserId);

    ConnectionRequest getConnectionRequest(UUID targetId, UUID loggedInUserId);
}
