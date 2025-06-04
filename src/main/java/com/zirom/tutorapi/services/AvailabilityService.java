package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.availability.OwnAvailabilitySchedule;
import com.zirom.tutorapi.domain.entities.availability.Duration;
import com.zirom.tutorapi.domain.entities.availability.Preference;
import com.zirom.tutorapi.domain.entities.availability.Schedule;

import java.util.List;
import java.util.UUID;

public interface AvailabilityService {
    List<Duration> createDurations(List<Duration> durationList);
    List<Schedule> createSchedules(List<Schedule> schedules);
    Preference createPreference(Preference preference);

    void deletePreference(UUID userId);
    void deleteSchedules(UUID userId);

    OwnAvailabilitySchedule getOwnAvailabilitySchedule(UUID userId);
    OwnAvailabilitySchedule changeOwnAvailabilitySchedule(OwnAvailabilitySchedule ownAvailabilitySchedule, UUID userId);
}
