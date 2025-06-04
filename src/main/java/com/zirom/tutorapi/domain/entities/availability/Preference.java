package com.zirom.tutorapi.domain.entities.availability;

import com.zirom.tutorapi.domain.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "preferences")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_preference_user",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"))
    private User user;

    @NotNull
    private int futureDays;

    @NotNull
    private double minNoticeHours;
}
