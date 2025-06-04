package com.zirom.tutorapi.domain.dtos.lesson;

import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservationDto {
    private UUID id;
    private LocalDateTime date;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private RequestState state;
}
