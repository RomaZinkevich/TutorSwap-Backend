package com.zirom.tutorapi.domain.entities.availability;

import com.zirom.tutorapi.domain.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "duration")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Duration {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_duration_user",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"))
    private User user;

    @NotNull
    private int duration;

}
