package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import com.zirom.tutorapi.services.AuthorizationService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Override
    public void hasAccessToChat(Chat chat, UUID userId) {
        if (!chat.getSenderUser().getId().equals(userId) && !chat.getReceiverUser().getId().equals(userId)){
            throw new AccessDeniedException("No rights to access this chat");
        }
    }

    @Override
    public void hasAccessToConnectionRequest(ConnectionRequest connectionRequest, UUID userId) {
        if (!connectionRequest.getSenderUser().getId().equals(userId) && !connectionRequest.getReceiverUser().getId().equals(userId)){
            throw new AccessDeniedException("No rights to access this chat");
        }
    }
}
