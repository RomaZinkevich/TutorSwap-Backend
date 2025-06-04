package com.zirom.tutorapi.domain.dtos.chat.messages.requests;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequestMessageRequest extends MessageRequest {
    private String description;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
}
