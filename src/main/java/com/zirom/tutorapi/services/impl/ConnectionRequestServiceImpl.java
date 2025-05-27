package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.MessageType;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.MessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.TextMessageRequest;
import com.zirom.tutorapi.domain.dtos.connection.CreateConnectionRequest;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.Connection;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.repositories.ConnectionRequestRepository;
import com.zirom.tutorapi.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConnectionRequestServiceImpl implements ConnectionRequestService {

    private final ConnectionRequestRepository connectionRequestRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final ConnectionService connectionService;
    private final ChatService chatService;
    private final MessageService messageService;

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

        Connection newConnection = new Connection();
        newConnection.setConnectionType(connectionRequest.getConnectionType());
        newConnection.setUser(connectionRequest.getSenderUser());
        newConnection.setPartnerUser(connectionRequest.getReceiverUser());
        connectionService.createConnection(newConnection);

        Chat newChat = new Chat();
        newChat.setSenderUser(connectionRequest.getSenderUser());
        newChat.setReceiverUser(connectionRequest.getReceiverUser());
        Chat chat = chatService.createChat(newChat);

        TextMessageRequest newMessageRequest = new TextMessageRequest();
        newMessageRequest.setContent(connectionRequest.getMessageContent());
        newMessageRequest.setChatId(chat.getId());
        newMessageRequest.setMessageType(MessageType.TEXT);
        newMessageRequest.setReceiverId(connectionRequest.getReceiverUser().getId());
        messageService.saveTextMessage(newMessageRequest, connectionRequest.getSenderUser().getId());

        return connectionRequestRepository.save(connectionRequest);
    }
}
