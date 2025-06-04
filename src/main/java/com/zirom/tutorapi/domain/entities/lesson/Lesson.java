package com.zirom.tutorapi.domain.entities.lesson;

import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_lesson_teacher",
                    foreignKeyDefinition = "FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE"))
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learner_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_lesson_learner",
                    foreignKeyDefinition = "FOREIGN KEY (learner_id) REFERENCES users(id) ON DELETE CASCADE"))
    private User learner;

    @Column(nullable = false)
    private LocalDateTime timeStart;

    @Column(nullable = false)
    private LocalDateTime timeEnd;

    @Column(nullable = false)
    private String googleMeetingUrl;
}
