package com.zirom.tutorapi.domain.dtos.availability;
import com.zirom.tutorapi.domain.entities.availability.Duration;
import com.zirom.tutorapi.domain.entities.availability.Preference;
import com.zirom.tutorapi.domain.entities.availability.Schedule;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailabilitySchedule {
    private List<Schedule> schedules;
    private Preference preference;
    private List<Duration> durations;
}
