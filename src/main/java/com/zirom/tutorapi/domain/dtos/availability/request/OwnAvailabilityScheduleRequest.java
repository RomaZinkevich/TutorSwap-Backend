package com.zirom.tutorapi.domain.dtos.availability.request;

import com.zirom.tutorapi.domain.dtos.availability.PreferenceDto;
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
public class OwnAvailabilityScheduleRequest {
    @NotNull(message = "schedule list is required")
    private List<ScheduleRequest> schedules;

    @NotNull(message = "preference is required")
    private PreferenceRequest preference;
}
