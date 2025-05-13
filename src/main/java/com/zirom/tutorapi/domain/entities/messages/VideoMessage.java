package com.zirom.tutorapi.domain.entities.messages;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "videomessages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VideoMessage {
    @Id
    private UUID messageId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "message_id", nullable = false)
    private BaseMessage message;

    @Column(nullable = false)
    private String videoUrl;

    @Column(nullable = false)
    private String caption;
}
