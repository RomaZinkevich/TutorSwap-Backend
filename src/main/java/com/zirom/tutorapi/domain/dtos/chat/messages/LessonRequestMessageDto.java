package com.zirom.tutorapi.domain.dtos.chat.messages;

import com.zirom.tutorapi.domain.RequestState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequestMessageDto extends MessageDto {
    private String description;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private RequestState state;
}
