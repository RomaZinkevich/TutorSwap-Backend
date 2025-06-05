package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.dtos.lesson.LessonDto;
import com.zirom.tutorapi.domain.dtos.user.OtherUserDto;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.lesson.Lesson;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.repositories.LessonRepository;
import com.zirom.tutorapi.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final UserMapper userMapper;

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

    @Override
    public List<LessonDto> getLessons(UUID id, boolean isLearner) {
        List<Lesson> lessons = isLearner ? lessonRepository.findAllByLearner_Id(id) : lessonRepository.findAllByTeacher_Id(id);
        List<LessonDto> lessonDtos = new ArrayList<>();
        lessons.forEach(lesson -> {
            LessonDto lessonDto = new LessonDto();
            lessonDto.setTimeStart(lesson.getTimeStart());
            lessonDto.setTimeEnd(lesson.getTimeEnd());
            lessonDto.setGoogleMeetingUrl(lesson.getGoogleMeetingUrl());
            User otherUser = isLearner ? lesson.getTeacher() : lesson.getLearner();
            OtherUserDto otherUserDto = userMapper.toOthersDto(otherUser);
            lessonDto.setOtherUser(otherUserDto);
            lessonDtos.add(lessonDto);
        });
        return lessonDtos;
    }
}
