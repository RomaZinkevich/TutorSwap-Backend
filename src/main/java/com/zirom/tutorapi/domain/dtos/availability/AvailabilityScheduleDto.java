package com.zirom.tutorapi.domain.dtos.availability;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailabilityScheduleDto {
    private List<ScheduleDto> schedules;
    private PreferenceDto preference;
}
