package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.Connection;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConnectionRepository extends JpaRepository<Connection, UUID> {

    @Query("SELECT c FROM Connection c WHERE c.user.id = :userId OR c.partnerUser.id = :userId")
    List<Connection> findAllByUserIdOrPartnerUserId(@Param("userId") UUID userId);

    @Query("""
        SELECT c FROM Connection c
        WHERE 
            (c.user.id = :currentUserId AND c.partnerUser.id = :targetUserId) OR
            (c.partnerUser.id = :currentUserId AND c.user.id = :targetUserId)
        """)
    Optional<Connection> findConnectionBetween(
            @Param("currentUserId") UUID currentUserId,
            @Param("targetUserId") UUID targetUserId
    );
}
