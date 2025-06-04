package com.zirom.tutorapi.domain.entities.messages;

import com.zirom.tutorapi.domain.MessageType;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BaseMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_message_chat",
                    foreignKeyDefinition = "FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE"))
    private Chat chat;

    @OneToOne(mappedBy = "message", fetch = FetchType.LAZY)
    private ImageMessage imageMessage;

    @OneToOne(mappedBy = "message", fetch = FetchType.LAZY)
    private TextMessage textMessage;

    @OneToOne(mappedBy = "message", fetch = FetchType.LAZY)
    private VideoMessage videoMessage;

    @OneToOne(mappedBy = "message", fetch = FetchType.LAZY)
    private ScheduleMessage scheduleMessage;

    @OneToOne(mappedBy = "message", fetch = FetchType.LAZY)
    private LessonRequestMessage lessonRequestMessage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
