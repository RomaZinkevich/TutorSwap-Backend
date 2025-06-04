package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.ConnectionRequestDirection;
import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.MessageType;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.TextMessageRequest;
import com.zirom.tutorapi.domain.dtos.connection.CreateConnectionRequest;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.Connection;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.repositories.ConnectionRequestRepository;
import com.zirom.tutorapi.services.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Tag(name="Image")
public class ConnectionRequestServiceImpl implements ConnectionRequestService {

    private final ConnectionRequestRepository connectionRequestRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final ConnectionService connectionService;
    private final ChatService chatService;
    private final MessageService messageService;

    @Override
    public List<ConnectionRequest> getConnectionsByUser(UUID receiverId, RequestState state, ConnectionRequestDirection direction) {
        if (direction == ConnectionRequestDirection.RECEIVED) return connectionRequestRepository.findConnectionRequestsByReceiverUser_IdAndRequestState(receiverId, state);
        return connectionRequestRepository.findConnectionRequestsBySenderUser_IdAndRequestState(receiverId, state);
    }

    @Override
    @Transactional
    public ConnectionRequest addConnectionRequest(UserDto loggedinUserDto, CreateConnectionRequest createConnectionRequest) {
        ConnectionRequest newConnectionRequest = new ConnectionRequest();

        User loggedinUser = userMapper.toEntity(loggedinUserDto);
        UUID receiverId = createConnectionRequest.getReceiverId();
        User receiverUser = userService.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + receiverId));;
        boolean exists = connectionRequestRepository.existsBySenderUserAndReceiverUser(loggedinUser, receiverUser) ||
                connectionRequestRepository.existsBySenderUserAndReceiverUser(receiverUser, loggedinUser);
        if (exists) {
            throw new IllegalStateException("A connection already exists between those two users");
        }

        newConnectionRequest.setSenderUser(loggedinUser);
        newConnectionRequest.setReceiverUser(receiverUser);

        newConnectionRequest.setMessageContent(createConnectionRequest.getMessage());

        return connectionRequestRepository.save(newConnectionRequest);
    }

    @Override
    @Transactional
    public ConnectionRequest updateConnectionRequest(UUID targetId, UUID loggedInUserId, boolean isAccepted) {
        ConnectionRequest connectionRequest = connectionRequestRepository.findConnectionBetween(targetId, loggedInUserId).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        if (!connectionRequest.getReceiverUser().getId().equals(loggedInUserId)) {
            throw new AccessDeniedException("No rights to access this Connection Request");
        }

        if (!isAccepted) {
            connectionRequest.setRequestState(RequestState.REJECTED);
            return connectionRequestRepository.save(connectionRequest);
        }
        connectionRequest.setRequestState(RequestState.ACCEPTED);

        Connection newConnection = new Connection();
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
        messageService.saveMessage(newMessageRequest, connectionRequest.getSenderUser().getId());

        return connectionRequestRepository.save(connectionRequest);
    }

    @Override
    public void deleteConnectionRequest(UUID targetId, UUID loggedInUserId) {
        connectionRequestRepository.findConnectionBetween(targetId, loggedInUserId).ifPresent(request -> {
            boolean isPendingAndSender = request.getRequestState() == RequestState.PENDING &&
                    request.getSenderUser().getId().equals(loggedInUserId);

            boolean isRejectedAndReceiver = request.getRequestState() == RequestState.REJECTED &&
                    request.getReceiverUser().getId().equals(loggedInUserId);

            if (isPendingAndSender || isRejectedAndReceiver) {
                connectionRequestRepository.delete(request);
            } else {
                throw new AccessDeniedException("Unauthorized to delete this Connection Request");
            }
        });
    }

    @Override
    public ConnectionRequest getConnectionRequest(UUID targetId, UUID loggedInUserId) {
        return connectionRequestRepository.findConnectionBetween(targetId, loggedInUserId).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }
}
