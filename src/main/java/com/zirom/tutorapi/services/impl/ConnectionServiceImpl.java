package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.entities.Connection;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import com.zirom.tutorapi.repositories.ChatRepository;
import com.zirom.tutorapi.repositories.ConnectionRepository;
import com.zirom.tutorapi.repositories.ConnectionRequestRepository;
import com.zirom.tutorapi.services.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final ConnectionRequestRepository connectionRequestRepository;
    private final ChatRepository chatRepository;

    @Override
    public Connection createConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

    @Override
    public List<Connection> getAllConnectionsByUserId(UUID id) {
        return connectionRepository.findAllByUserIdOrPartnerUserId(id);
    }

    @Override
    public void deleteConnection(UUID targetUserId, UUID loggedInUserId) {
        connectionRepository.findConnectionBetween(loggedInUserId, targetUserId).ifPresent(connectionRepository::delete);
        connectionRequestRepository.findConnectionBetween(loggedInUserId, targetUserId).ifPresent(connectionRequestRepository::delete);
        chatRepository.findChatBetween(loggedInUserId, targetUserId).ifPresent(chatRepository::delete);
    }
}
