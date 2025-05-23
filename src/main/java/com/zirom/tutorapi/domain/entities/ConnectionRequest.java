package com.zirom.tutorapi.domain.entities;

import com.zirom.tutorapi.domain.ConnectionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name="connection_requests",
        uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id", "receiver_id"})
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConnectionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User senderUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiverUser;

    @Column(nullable = false)
    private String messageContent;

    @Column(nullable = false)
    private boolean accepted;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConnectionType connectionType;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.accepted = false;
    }
}
