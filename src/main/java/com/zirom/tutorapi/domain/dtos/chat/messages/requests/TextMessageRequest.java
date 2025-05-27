package com.zirom.tutorapi.domain.dtos.chat.messages.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TextMessageRequest extends MessageRequest {
    private String content;
}
