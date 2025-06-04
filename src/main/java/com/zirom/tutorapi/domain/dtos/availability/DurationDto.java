package com.zirom.tutorapi.domain.dtos.availability;

import com.zirom.tutorapi.domain.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DurationDto {
    private UUID id;
    private int duration;
}
