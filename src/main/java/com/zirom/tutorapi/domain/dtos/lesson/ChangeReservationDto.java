package com.zirom.tutorapi.domain.dtos.lesson;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeReservationDto {
    @NotNull(message="is accepted is required")
    private boolean isAccepted;

    private String googleMeetUrl;
}
