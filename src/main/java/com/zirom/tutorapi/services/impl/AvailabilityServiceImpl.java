package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.dtos.availability.AvailabilitySchedule;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.availability.Duration;
import com.zirom.tutorapi.domain.entities.availability.Preference;
import com.zirom.tutorapi.domain.entities.availability.Schedule;
import com.zirom.tutorapi.repositories.DurationRepository;
import com.zirom.tutorapi.repositories.PreferenceRepository;
import com.zirom.tutorapi.repositories.ScheduleRepository;
import com.zirom.tutorapi.services.AvailabilityService;
import com.zirom.tutorapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final ScheduleRepository scheduleRepository;
    private final DurationRepository durationRepository;
    private final PreferenceRepository preferenceRepository;
    private final UserService userService;

    @Override
    public List<Duration> createDurations(List<Duration> durationList) {
        return durationRepository.saveAll(durationList);
    }

    @Override
    public List<Schedule> createSchedules(List<Schedule> schedules) {
        return scheduleRepository.saveAll(schedules);
    }

    @Override
    public Preference createPreference(Preference preference) {
        return preferenceRepository.save(preference);
    }

    @Override
    public void deletePreference(UUID userId) {
        preferenceRepository.findByUser_Id(userId)
                .ifPresent(preferenceRepository::delete);
    }

    @Override
    public void deleteSchedules(UUID userId) {
        scheduleRepository.deleteAll(scheduleRepository.findAllByUser_Id(userId));
    }

    @Override
    public AvailabilitySchedule getAvailabilitySchedule(UUID userId) {
        List<Schedule> schedules = scheduleRepository.findAllByUser_Id(userId);
        Preference preference = preferenceRepository.findByUser_Id(userId).orElseThrow(EntityNotFoundException::new);
        AvailabilitySchedule ownAvailability = new AvailabilitySchedule();
        ownAvailability.setPreference(preference);
        ownAvailability.setSchedules(schedules);
        return ownAvailability;
    }

    @Override
    @Transactional
    public AvailabilitySchedule changeOwnAvailabilitySchedule(AvailabilitySchedule availabilitySchedule, UUID userId) {
        User user = userService.findById(userId).orElseThrow(EntityNotFoundException::new);
        Optional<Preference> oldPreference = preferenceRepository.findByUser_Id(userId);
        List<Schedule> oldSchedules = scheduleRepository.findAllByUser_Id(userId);
        Preference newPreference = availabilitySchedule.getPreference();
        newPreference.setUser(user);
        List<Schedule> newSchedules = availabilitySchedule.getSchedules();
        newSchedules.forEach(schedule -> {schedule.setUser(user);});

        Preference preference = createPreference(newPreference);
        List<Schedule> schedules = createSchedules(newSchedules);

        oldPreference.ifPresent(preferenceRepository::delete);
        oldSchedules.forEach(scheduleRepository::delete);

        AvailabilitySchedule newSchedule = new AvailabilitySchedule();
        newSchedule.setPreference(preference);
        newSchedule.setSchedules(schedules);
        return newSchedule;
    }
}
