package com.zirom.tutorapi.domain.entities;

import com.zirom.tutorapi.domain.ConnectionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="connections")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Connection {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private User partnerUser;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConnectionType connectionType;
}
