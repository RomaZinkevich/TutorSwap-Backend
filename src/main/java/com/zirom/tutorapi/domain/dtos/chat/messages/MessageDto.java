package com.zirom.tutorapi.domain.dtos.chat.messages;

import com.zirom.tutorapi.domain.MessageType;
import com.zirom.tutorapi.domain.dtos.chat.ChatDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextMessageDto.class, name = "text"),
        @JsonSubTypes.Type(value = ImageMessageDto.class, name = "image"),
        @JsonSubTypes.Type(value = VideoMessageDto.class, name = "video")
})
public abstract class MessageDto {
    private UUID id;
    private UserDto sender;
    private UserDto receiver;
    private MessageType messageType;
    private LocalDateTime timestamp;
}
