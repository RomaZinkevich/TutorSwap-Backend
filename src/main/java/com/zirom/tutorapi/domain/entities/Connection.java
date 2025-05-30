package com.zirom.tutorapi.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name="connections",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "partner_id"})
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@org.hibernate.annotations.Check(constraints = "partner_id <> user_id")
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private User partnerUser;
}
