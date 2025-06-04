package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.MessageType;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.ImageMessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.MessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.ScheduleMessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.TextMessageRequest;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import com.zirom.tutorapi.domain.entities.messages.ImageMessage;
import com.zirom.tutorapi.domain.entities.messages.ScheduleMessage;
import com.zirom.tutorapi.domain.entities.messages.TextMessage;
import com.zirom.tutorapi.repositories.ScheduleRepository;
import com.zirom.tutorapi.repositories.messages.BaseMessageRepository;
import com.zirom.tutorapi.repositories.messages.ImageMessageRepository;
import com.zirom.tutorapi.repositories.messages.ScheduleMessageRepository;
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
    private final ImageMessageRepository imageMessageRepository;
    private final ScheduleMessageRepository scheduleMessageRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    @Transactional
    public BaseMessage saveMessage(MessageRequest messageRequest, UUID senderId) {
        User sender = userService.findById(senderId).orElseThrow(EntityNotFoundException::new);
        User receiver = userService.findById(messageRequest.getReceiverId()).orElseThrow(EntityNotFoundException::new);
        Chat chat = chatService.getChatById(messageRequest.getChatId()).orElseThrow(EntityNotFoundException::new);
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setMessageType(messageRequest.getMessageType());
        baseMessage.setSender(sender);
        baseMessage.setReceiver(receiver);
        baseMessage.setChat(chat);
        BaseMessage savedMessage = baseMessageRepository.save(baseMessage);

        if (messageRequest instanceof TextMessageRequest text) saveTextMessage(savedMessage, text.getContent());
        if (messageRequest instanceof ImageMessageRequest image) saveImageMessage(savedMessage, image);
        if (messageRequest instanceof ScheduleMessageRequest) saveScheduleMessage(savedMessage);

        return savedMessage;
    }


    private void saveTextMessage(BaseMessage savedMessage, String content) {
        TextMessage textMessage = new TextMessage();
        textMessage.setMessage(savedMessage);
        textMessage.setContent(content);
        textMessageRepository.save(textMessage);
    }

    private void saveImageMessage(BaseMessage messageRequest, ImageMessageRequest image) {
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setMessage(messageRequest);
        imageMessage.setImageUrl(image.getImageUrl());
        imageMessage.setCaption(image.getCaption());
        imageMessageRepository.save(imageMessage);
    }

    private void saveScheduleMessage(BaseMessage messageRequest) {
        ScheduleMessage scheduleMessage = new ScheduleMessage();
        scheduleMessage.setMessage(messageRequest);
        scheduleMessageRepository.save(scheduleMessage);
    }
}
