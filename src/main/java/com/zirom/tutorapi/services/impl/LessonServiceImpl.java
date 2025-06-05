package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.entities.lesson.Lesson;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;
import com.zirom.tutorapi.repositories.LessonRepository;
import com.zirom.tutorapi.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public Lesson createLesson(Reservation reservation, String googleMeetUrl) {
        if (googleMeetUrl == null || googleMeetUrl.isEmpty()) throw new IllegalArgumentException("googleMeetUrl is empty");
        Lesson lesson = new Lesson();
        lesson.setTeacher(reservation.getUser());
        lesson.setLearner(reservation.getLearner());
        lesson.setTimeStart(reservation.getTimeStart());
        lesson.setTimeEnd(reservation.getTimeEnd());
        lesson.setGoogleMeetingUrl(googleMeetUrl);
        return lessonRepository.save(lesson);
    }
}
