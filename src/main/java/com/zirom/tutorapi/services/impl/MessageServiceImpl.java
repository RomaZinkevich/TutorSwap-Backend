package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.MessageType;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.*;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.messages.*;
import com.zirom.tutorapi.repositories.ScheduleRepository;
import com.zirom.tutorapi.repositories.messages.*;
import com.zirom.tutorapi.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final BaseMessageRepository baseMessageRepository;
    private final TextMessageRepository textMessageRepository;
    private final ImageMessageRepository imageMessageRepository;
    private final ScheduleMessageRepository scheduleMessageRepository;
    private final UserService userService;
    private final ChatService chatService;
    private final LessonRequestMessageRepository lessonRequestMessageRepository;
    private final ReservationService reservationService;

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
        if (messageRequest instanceof LessonRequestMessageRequest lessonRequest) saveLessonRequestMessage(savedMessage, lessonRequest);

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

    private void saveLessonRequestMessage(BaseMessage messageRequest, LessonRequestMessageRequest lessonRequest) {
        LocalDateTime timeStart = lessonRequest.getTimeStart();
        LocalDateTime timeEnd = lessonRequest.getTimeEnd();

        LessonRequestMessage requestMessage = new LessonRequestMessage();
        requestMessage.setMessage(messageRequest);
        requestMessage.setDescription(lessonRequest.getDescription());
        requestMessage.setTimeStart(timeStart);
        requestMessage.setTimeEnd(timeEnd);
        lessonRequestMessageRepository.save(requestMessage);

        UUID receiverId = messageRequest.getReceiver().getId();
        UUID senderId = messageRequest.getSender().getId();
        reservationService.createReservatiion(receiverId, senderId, timeStart, timeEnd);
    }
}
