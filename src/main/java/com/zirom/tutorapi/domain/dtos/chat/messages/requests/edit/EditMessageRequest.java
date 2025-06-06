package com.zirom.tutorapi.domain.dtos.chat.messages.requests.edit;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zirom.tutorapi.domain.MessageType;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        @JsonSubTypes.Type(value = LessonEditMessageRequest.class, name = "LESSON")
})
public abstract class EditMessageRequest {
    private UUID messageId;
    private UUID chatId;
    private MessageType messageType;

}
