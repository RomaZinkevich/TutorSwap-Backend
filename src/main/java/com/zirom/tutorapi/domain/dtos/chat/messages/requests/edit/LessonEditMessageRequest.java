package com.zirom.tutorapi.domain.dtos.chat.messages.requests.edit;

import com.zirom.tutorapi.domain.dtos.lesson.ChangeReservationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonEditMessageRequest extends EditMessageRequest {
    private ChangeReservationRequest changeReservationRequest;
}
