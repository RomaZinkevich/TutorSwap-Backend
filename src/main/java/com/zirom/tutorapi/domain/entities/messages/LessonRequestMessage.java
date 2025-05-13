package com.zirom.tutorapi.domain.entities.messages;

import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "message_id", nullable = false)
    private BaseMessage message;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean approved;

    @PrePersist
    protected void onCreate() {
        this.approved = false;
    }
}
