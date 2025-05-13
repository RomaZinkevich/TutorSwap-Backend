package com.zirom.tutorapi.domain.entities.messages;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="schedulemessages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ScheduleMessage {
    @Id
    private UUID messageId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "message_id", nullable = false)
    private BaseMessage message;
}
