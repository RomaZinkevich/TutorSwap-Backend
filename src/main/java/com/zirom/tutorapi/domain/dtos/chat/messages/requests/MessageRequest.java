package com.zirom.tutorapi.domain.dtos.chat.messages.requests;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zirom.tutorapi.domain.MessageType;
import com.zirom.tutorapi.domain.dtos.chat.messages.ImageMessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.TextMessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.VideoMessageDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "messageType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextMessageRequest.class, name = "TEXT"),
        @JsonSubTypes.Type(value = ImageMessageRequest.class, name = "IMAGE"),
        @JsonSubTypes.Type(value = VideoMessageRequest.class, name = "VIDEO"),
        @JsonSubTypes.Type(value = ScheduleMessageRequest.class, name = "SCHEDULE")
})
public abstract class MessageRequest {
    @NotNull(message = "chat id is required")
    private UUID chatId;

    @NotNull(message = "receiver id is required")
    private UUID receiverId;

    @NotNull(message = "message type is required")
    private MessageType messageType;
}
