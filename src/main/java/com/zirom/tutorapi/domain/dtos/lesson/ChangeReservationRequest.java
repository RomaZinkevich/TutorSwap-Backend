package com.zirom.tutorapi.domain.dtos.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeReservationRequest {
    @NotNull(message="is accepted is required")
    @JsonProperty("isAccepted")
    private boolean isAccepted;

    private String googleMeetUrl;
}
