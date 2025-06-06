package com.zirom.tutorapi.domain.entities.messages;

import com.zirom.tutorapi.domain.entities.lesson.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="lessonrequestmessages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LessonRequestMessage {
    @Id
    private UUID messageId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "message_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_lessonrequestmessage_basemessage",
                    foreignKeyDefinition = "FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE"))
    private BaseMessage message;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime timeStart;

    @Column(nullable = false)
    private LocalDateTime timeEnd;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_lessonmessage_reservation",
                    foreignKeyDefinition = "FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE"))
    private Reservation reservation;
}
