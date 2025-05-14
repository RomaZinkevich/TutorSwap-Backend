package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.chat.messages.ImageMessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.MessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.TextMessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.VideoMessageDto;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import com.zirom.tutorapi.domain.entities.messages.ImageMessage;
import com.zirom.tutorapi.domain.entities.messages.TextMessage;
import com.zirom.tutorapi.domain.entities.messages.VideoMessage;
import com.zirom.tutorapi.repositories.messages.ImageMessageRepository;
import com.zirom.tutorapi.repositories.messages.TextMessageRepository;
import com.zirom.tutorapi.repositories.messages.VideoMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageMapper {
    private final TextMessageRepository textMessageRepository;
    private final ImageMessageRepository imageMessageRepository;
    private final VideoMessageRepository videoMessageRepository;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;

    public MessageDto toDto(BaseMessage message) {
        switch (message.getMessageType()) {
            case TEXT:
                TextMessage text = textMessageRepository.findById(message.getId()).orElseThrow();
                TextMessageDto textDto = new TextMessageDto();

                //Common fields
                textDto.setId(message.getId());
                textDto.setSender(userMapper.toDto(message.getSender()));
                textDto.setReceiver(userMapper.toDto(message.getReceiver()));
                textDto.setChat(chatMapper.toDto(message.getChat()));
                textDto.setMessageType(message.getMessageType());
                textDto.setTimestamp(message.getTimestamp());

                // Specific fields
                textDto.setContent(text.getContent());

                return textDto;

            case IMAGE:
                ImageMessage img = imageMessageRepository.findById(message.getId()).orElseThrow();
                ImageMessageDto imgDto = new ImageMessageDto();

                //Common fields
                imgDto.setId(message.getId());
                imgDto.setSender(userMapper.toDto(message.getSender()));
                imgDto.setReceiver(userMapper.toDto(message.getReceiver()));
                imgDto.setChat(chatMapper.toDto(message.getChat()));
                imgDto.setMessageType(message.getMessageType());
                imgDto.setTimestamp(message.getTimestamp());

                // Specific fields
                imgDto.setImageUrl(img.getImageUrl());
                imgDto.setCaption(img.getCaption());

                return imgDto;

            case VIDEO:
                VideoMessage vid = videoMessageRepository.findById(message.getId()).orElseThrow();
                VideoMessageDto vidDto = new VideoMessageDto();

                //Common fields
                vidDto.setId(message.getId());
                vidDto.setSender(userMapper.toDto(message.getSender()));
                vidDto.setReceiver(userMapper.toDto(message.getReceiver()));
                vidDto.setChat(chatMapper.toDto(message.getChat()));
                vidDto.setMessageType(message.getMessageType());
                vidDto.setTimestamp(message.getTimestamp());

                // Specific fields
                vidDto.setVideoUrl(vid.getVideoUrl());
                vidDto.setCaption(vid.getCaption());

                return vidDto;

            default:
                throw new IllegalArgumentException("Unsupported message type");
        }
    }
}
