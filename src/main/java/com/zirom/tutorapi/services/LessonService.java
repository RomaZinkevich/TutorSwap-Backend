package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.entities.lesson.Lesson;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;

public interface LessonService {
    Lesson createLesson(Reservation reservation, String googleMeetUrl);
}
