package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.chat.messages.ImageMessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.MessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.TextMessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.VideoMessageDto;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import com.zirom.tutorapi.domain.entities.messages.ImageMessage;
import com.zirom.tutorapi.domain.entities.messages.TextMessage;
import com.zirom.tutorapi.domain.entities.messages.VideoMessage;
import com.zirom.tutorapi.repositories.messages.ImageMessageRepository;
import com.zirom.tutorapi.repositories.messages.TextMessageRepository;
import com.zirom.tutorapi.repositories.messages.VideoMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class MessageMapper {
    private final TextMessageRepository textMessageRepository;
    private final ImageMessageRepository imageMessageRepository;
    private final VideoMessageRepository videoMessageRepository;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;

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

            default:
                throw new IllegalArgumentException("Unsupported message type");
        }

        // Common fields
        boolean isSender = message.getSender().getId() == loggedInUserId;

        dto.setId(message.getId());
        dto.setSender(isSender);
        dto.setMessageType(message.getMessageType());
        dto.setTimestamp(message.getTimestamp());

        return dto;
    }
}
