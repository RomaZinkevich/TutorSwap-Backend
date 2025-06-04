package com.zirom.tutorapi.domain.dtos.availability;

import com.zirom.tutorapi.domain.entities.availability.Preference;
import com.zirom.tutorapi.domain.entities.availability.Schedule;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnAvailabilityScheduleDto {
    private List<ScheduleDto> schedules;
    private PreferenceDto preference;
}
