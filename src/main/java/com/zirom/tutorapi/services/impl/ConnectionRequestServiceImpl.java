package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.dtos.ConnectionRequestDto;
import com.zirom.tutorapi.domain.dtos.CreateConnectionRequest;
import com.zirom.tutorapi.domain.dtos.UserDto;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.repositories.ConnectionRequestRepository;
import com.zirom.tutorapi.services.ConnectionRequestService;
import com.zirom.tutorapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConnectionRequestServiceImpl implements ConnectionRequestService {

    private final ConnectionRequestRepository connectionRequestRepository;
    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public List<ConnectionRequest> getConnectionsByUser(UUID receiverId, boolean isAccepted) {
        return connectionRequestRepository.findConnectionRequestsByReceiverUser_IdAndAccepted(receiverId, isAccepted);
    }

    @Override
    @Transactional
    public ConnectionRequest addConnectionRequest(UserDto loggedinUserDto, CreateConnectionRequest createConnectionRequest) {
        ConnectionRequest newConnectionRequest = new ConnectionRequest();

        User loggedinUser = userMapper.toEntity(loggedinUserDto);
        newConnectionRequest.setSenderUser(loggedinUser);

        UUID receiverId = createConnectionRequest.getReceiverId();
        User receiverUser = userService.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + receiverId));;
        newConnectionRequest.setReceiverUser(receiverUser);

        newConnectionRequest.setMessageContent(createConnectionRequest.getMessage());
        newConnectionRequest.setConnectionType(createConnectionRequest.getConnectionType());

        return connectionRequestRepository.save(newConnectionRequest);
    }

    @Override
    @Transactional
    public ConnectionRequest updateAccepted(UUID id, UserDto loggedinUserDto) {
        ConnectionRequest connectionRequest = connectionRequestRepository.findConnectionRequestByIdAndReceiverUser_Id(
                id, loggedinUserDto.getId()
        ).orElseThrow(() -> new EntityNotFoundException("Connection request with matching id and receiver's id not found"));
        connectionRequest.setAccepted(true);
        return connectionRequestRepository.save(connectionRequest);
    }
}
