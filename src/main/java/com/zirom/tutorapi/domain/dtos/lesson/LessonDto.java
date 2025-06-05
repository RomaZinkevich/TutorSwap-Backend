package com.zirom.tutorapi.domain.dtos.lesson;

import com.zirom.tutorapi.domain.dtos.user.OtherUserDto;
import com.zirom.tutorapi.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class LessonDto {
    private OtherUserDto otherUser;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private String googleMeetingUrl;
}
