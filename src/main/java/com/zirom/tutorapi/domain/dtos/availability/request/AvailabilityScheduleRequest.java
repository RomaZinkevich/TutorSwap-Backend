package com.zirom.tutorapi.domain.dtos.availability.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityScheduleRequest {
    @NotNull(message = "schedule list is required")
    @Valid
    private List<ScheduleRequest> schedules;

    @NotNull(message = "preference is required")
    @Valid
    private PreferenceRequest preference;

    @NotNull(message = "durations is required")
    @Valid
    private List<DurationRequest> durations;
}
