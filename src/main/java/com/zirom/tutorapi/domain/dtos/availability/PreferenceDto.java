package com.zirom.tutorapi.domain.dtos.availability;

import com.zirom.tutorapi.domain.entities.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreferenceDto {
    private UUID id;
    private int futureDays;
    private double minNoticeHours;
}
