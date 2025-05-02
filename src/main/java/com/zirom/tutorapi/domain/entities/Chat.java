package com.zirom.tutorapi.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "chats")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User senderUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiverUser;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Chat chat)) return false;
        return Objects.equals(id, chat.id) && Objects.equals(senderUser, chat.senderUser) && Objects.equals(receiverUser, chat.receiverUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderUser, receiverUser);
    }
}
