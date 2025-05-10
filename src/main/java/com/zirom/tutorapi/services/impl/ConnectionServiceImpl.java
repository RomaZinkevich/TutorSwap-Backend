package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.entities.Connection;
import com.zirom.tutorapi.repositories.ConnectionRepository;
import com.zirom.tutorapi.services.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Override
    public Connection createConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

    @Override
    public List<Connection> getAllConnectionsByUserId(UUID id) {
        return connectionRepository.findAllByUserIdOrPartnerUserId(id);
    }
}
