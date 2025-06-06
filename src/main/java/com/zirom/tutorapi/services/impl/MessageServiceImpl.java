package com.zirom.tutorapi.services.impl;

import com.nimbusds.oauth2.sdk.Message;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.*;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.edit.EditMessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.edit.LessonEditMessageRequest;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;
import com.zirom.tutorapi.domain.entities.messages.*;
import com.zirom.tutorapi.repositories.messages.*;
import com.zirom.tutorapi.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final BaseMessageRepository baseMessageRepository;
    private final TextMessageRepository textMessageRepository;
    private final ImageMessageRepository imageMessageRepository;
    private final VideoMessageRepository videoMessageRepository;
    private final ScheduleMessageRepository scheduleMessageRepository;
    private final UserService userService;
    private final ChatService chatService;
    private final LessonRequestMessageRepository lessonRequestMessageRepository;
    private final ReservationService reservationService;
    private final AuthorizationService authorizationService;

    @Override
    @Transactional
    public BaseMessage saveMessage(MessageRequest messageRequest, UUID senderId) {
        Chat chat = chatService.getChatById(messageRequest.getChatId()).orElseThrow(EntityNotFoundException::new);
        authorizationService.hasAccessToChat(chat, senderId);
        User sender = userService.findById(senderId).orElseThrow(EntityNotFoundException::new);
        User receiver = userService.findById(messageRequest.getReceiverId()).orElseThrow(EntityNotFoundException::new);
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setMessageType(messageRequest.getMessageType());
        baseMessage.setSender(sender);
        baseMessage.setReceiver(receiver);
        baseMessage.setChat(chat);
        BaseMessage savedMessage = baseMessageRepository.save(baseMessage);

        if (messageRequest instanceof TextMessageRequest text) saveTextMessage(savedMessage, text.getContent());
        if (messageRequest instanceof ImageMessageRequest image) saveImageMessage(savedMessage, image);
        if (messageRequest instanceof VideoMessageRequest video) saveVideoMessage(savedMessage, video);
        if (messageRequest instanceof ScheduleMessageRequest) saveScheduleMessage(savedMessage);
        if (messageRequest instanceof LessonRequestMessageRequest lessonRequest) saveLessonRequestMessage(savedMessage, lessonRequest);

        return savedMessage;
    }

    @Override
    public BaseMessage editMessage(UUID loggedInUserId, EditMessageRequest editMessageRequest) {
        BaseMessage baseMessage = baseMessageRepository.findById(editMessageRequest.getMessageId()).orElseThrow(EntityNotFoundException::new);

        if (editMessageRequest instanceof LessonEditMessageRequest lessonEditRequest) editLessonRequestMessage(lessonEditRequest, loggedInUserId);

        return baseMessage;
    }

    private void editLessonRequestMessage(LessonEditMessageRequest lessonEditMessageRequest, UUID loggedInUserId) {
        LessonRequestMessage requestMessage = lessonRequestMessageRepository.findById(lessonEditMessageRequest.getMessageId()).orElseThrow(EntityNotFoundException::new);
        UUID reservationId = requestMessage.getReservation().getId();
        reservationService.changeReservation(loggedInUserId, reservationId, lessonEditMessageRequest.getChangeReservationDto());
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

    private void saveVideoMessage(BaseMessage messageRequest, VideoMessageRequest video) {
        VideoMessage videoMessage = new VideoMessage();
        videoMessage.setMessage(messageRequest);
        videoMessage.setVideoUrl(video.getVideoUrl());
        videoMessage.setCaption(video.getCaption());
        videoMessageRepository.save(videoMessage);
    }

    private void saveScheduleMessage(BaseMessage messageRequest) {
        ScheduleMessage scheduleMessage = new ScheduleMessage();
        scheduleMessage.setMessage(messageRequest);
        scheduleMessageRepository.save(scheduleMessage);
    }

    private void saveLessonRequestMessage(BaseMessage messageRequest, LessonRequestMessageRequest lessonRequest) {
        LocalDateTime timeStart = lessonRequest.getTimeStart();
        LocalDateTime timeEnd = lessonRequest.getTimeEnd();

        UUID receiverId = messageRequest.getReceiver().getId();
        UUID senderId = messageRequest.getSender().getId();
        Reservation reservation = reservationService.createReservation(receiverId, senderId, timeStart, timeEnd);

        LessonRequestMessage requestMessage = new LessonRequestMessage();
        requestMessage.setMessage(messageRequest);
        requestMessage.setDescription("Placeholder");
        requestMessage.setTimeStart(timeStart);
        requestMessage.setTimeEnd(timeEnd);
        requestMessage.setReservation(reservation);

        lessonRequestMessageRepository.save(requestMessage);
    }
}
