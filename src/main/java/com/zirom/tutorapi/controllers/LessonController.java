package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.lesson.LessonDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.lesson.Lesson;
import com.zirom.tutorapi.mappers.LessonMapper;
import com.zirom.tutorapi.security.ApiUserDetails;
import com.zirom.tutorapi.services.LessonService;
import com.zirom.tutorapi.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/v1/lessons")
@RequiredArgsConstructor
@Tag(name="Lesson")
public class LessonController {

    private final LessonService lessonService;
    private final UserService userService;
    private final LessonMapper lessonMapper;

    @GetMapping("/me")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<LessonDto>> getLessons(
            @RequestParam(required=false, defaultValue = "true") boolean isLearner,
            Authentication authentication
    ) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        List<Lesson> lessons = lessonService.getLessons(user.getId(), isLearner);
        List<LessonDto> lessonDtos = lessons.stream().map(lesson ->  lessonMapper.toDto(lesson, isLearner)).toList();
        return ResponseEntity.ok(lessonDtos);
    }
}
