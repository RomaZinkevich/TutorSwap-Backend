package com.zirom.tutorapi.domain.dtos.availability.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {
    @NotNull(message = "Day of the week must be provided")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time must be provided")
    private LocalTime startTime;

    @NotNull(message = "End time must be provided")
    private LocalTime endTime;
}
