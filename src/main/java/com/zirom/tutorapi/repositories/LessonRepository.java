package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
}
