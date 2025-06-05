package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.lesson.LessonDto;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.lesson.Lesson;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public abstract class LessonMapper {
    @Autowired
    protected UserMapper userMapper;

    public LessonDto toDto(Lesson lesson, boolean isLearner) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setTimeStart(lesson.getTimeStart());
        lessonDto.setTimeEnd(lesson.getTimeEnd());
        lessonDto.setGoogleMeetingUrl(lesson.getGoogleMeetingUrl());

        User otherUser = isLearner ? lesson.getTeacher() : lesson.getLearner();
        lessonDto.setOtherUser(userMapper.toOthersDto(otherUser));

        return lessonDto;
    }
}
