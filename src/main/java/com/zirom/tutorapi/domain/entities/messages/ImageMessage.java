package com.zirom.tutorapi.domain.entities.messages;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "imagemessages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImageMessage {

    @Id
    private UUID messageId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "message_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_imagemessage_basemessage",
                    foreignKeyDefinition = "FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE"))
    private BaseMessage message;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String caption;
}
