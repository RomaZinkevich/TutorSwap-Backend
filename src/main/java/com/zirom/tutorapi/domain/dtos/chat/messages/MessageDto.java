package com.zirom.tutorapi.domain.dtos.chat.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        property = "messageType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextMessageDto.class, name = "TEXT"),
        @JsonSubTypes.Type(value = ImageMessageDto.class, name = "IMAGE"),
        @JsonSubTypes.Type(value = VideoMessageDto.class, name = "VIDEO")
})
public abstract class MessageDto {
    private UUID id;
    private MessageType messageType;
    private LocalDateTime timestamp;
    private UUID senderId;
}
