package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.dtos.chat.messages.*;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.messages.*;
import com.zirom.tutorapi.repositories.ReservationRepository;
import com.zirom.tutorapi.repositories.messages.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class MessageMapper {
    private final TextMessageRepository textMessageRepository;
    private final ImageMessageRepository imageMessageRepository;
    private final VideoMessageRepository videoMessageRepository;
    private final LessonRequestMessageRepository lessonRequestMessageRepository;
    private final ReservationRepository reservationRepository;

    public MessageDto toDto(BaseMessage message, UUID loggedInUserId) {
        MessageDto dto;

        switch (message.getMessageType()) {
            case TEXT:
                TextMessage text = textMessageRepository.findById(message.getId()).orElseThrow();
                TextMessageDto textDto = new TextMessageDto();
                textDto.setContent(text.getContent());
                dto = textDto;
                break;

            case IMAGE:
                ImageMessage img = imageMessageRepository.findById(message.getId()).orElseThrow();
                ImageMessageDto imgDto = new ImageMessageDto();
                imgDto.setImageUrl(img.getImageUrl());
                imgDto.setCaption(img.getCaption());
                dto = imgDto;
                break;

            case VIDEO:
                VideoMessage vid = videoMessageRepository.findById(message.getId()).orElseThrow();
                VideoMessageDto vidDto = new VideoMessageDto();
                vidDto.setVideoUrl(vid.getVideoUrl());
                vidDto.setCaption(vid.getCaption());
                dto = vidDto;
                break;

            case SCHEDULE:
                dto = new ScheduleMessageDto();
                break;

            case LESSON:
                LessonRequestMessage lesson = lessonRequestMessageRepository.findById(message.getId()).orElseThrow();
                RequestState state = reservationRepository.findById(lesson.getReservation().getId()).orElseThrow().getState();
                LessonRequestMessageDto lessonDto = new LessonRequestMessageDto();
                lessonDto.setState(state);
                lessonDto.setDescription(lesson.getDescription());
                lessonDto.setTimeStart(lesson.getTimeStart());
                lessonDto.setTimeEnd(lesson.getTimeEnd());
                dto = lessonDto;
                break;

            default:
                throw new IllegalArgumentException("Unsupported message type");
        }

        // Common fields
        dto.setId(message.getId());
        dto.setSenderId(message.getSender().getId());
        dto.setMessageType(message.getMessageType());
        dto.setTimestamp(message.getTimestamp());

        return dto;
    }
}
