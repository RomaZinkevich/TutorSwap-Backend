package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.lesson.LessonDto;
import com.zirom.tutorapi.domain.entities.lesson.Lesson;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    Lesson createLesson(Reservation reservation, String googleMeetUrl);

    List<Lesson> getLessons(UUID id, boolean isLearner);
}
