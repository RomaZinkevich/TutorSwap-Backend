package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.entities.Connection;

import java.util.List;
import java.util.UUID;

public interface ConnectionService {
    Connection createConnection(Connection connection);

    List<Connection> getAllConnectionsByUserId(UUID id);

    void deleteConnection(UUID targetUserId, UUID loggedInUserId);
}
