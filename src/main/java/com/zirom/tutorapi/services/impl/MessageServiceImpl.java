package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.dtos.chat.messages.requests.MessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.TextMessageRequest;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import com.zirom.tutorapi.domain.entities.messages.TextMessage;
import com.zirom.tutorapi.repositories.messages.BaseMessageRepository;
import com.zirom.tutorapi.repositories.messages.TextMessageRepository;
import com.zirom.tutorapi.services.AuthorizationService;
import com.zirom.tutorapi.services.ChatService;
import com.zirom.tutorapi.services.MessageService;
import com.zirom.tutorapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final BaseMessageRepository baseMessageRepository;
    private final TextMessageRepository textMessageRepository;
    private final UserService userService;
    private final ChatService chatService;
    private final AuthorizationService authorizationService;

    @Override
    public List<BaseMessage> getAllMessagesByChatAndUserIds(UUID userId, UUID chatId) {
        List<BaseMessage> messages = baseMessageRepository.findAllByChat_IdOrderByTimestampAsc(chatId);
        if (messages.isEmpty()) throw new EntityNotFoundException();
        authorizationService.hasAccessToChat(messages.get(0).getChat(), userId);
        return messages;
    }

    @Override
    public BaseMessage getLastMessageByChatId(UUID chatId) {
        return baseMessageRepository.findTopByChat_IdOrderByTimestampDesc(chatId);
    }

    @Override
    @Transactional
    public BaseMessage saveTextMessage(TextMessageRequest messageRequest, UUID senderId) {
        User sender = userService.findById(senderId).orElseThrow(EntityNotFoundException::new);
        User receiver = userService.findById(messageRequest.getReceiverId()).orElseThrow(EntityNotFoundException::new);
        Chat chat = chatService.getChatById(messageRequest.getChatId()).orElseThrow(EntityNotFoundException::new);
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setMessageType(messageRequest.getMessageType());
        baseMessage.setSender(sender);
        baseMessage.setReceiver(receiver);
        baseMessage.setChat(chat);
        BaseMessage savedMessage = baseMessageRepository.save(baseMessage);

        TextMessage textMessage = new TextMessage();
        textMessage.setMessage(savedMessage);
        textMessage.setContent(messageRequest.getContent());
        textMessageRepository.save(textMessage);

        return savedMessage;
    }
}
