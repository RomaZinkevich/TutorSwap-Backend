package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, UUID> {
    List<ConnectionRequest> findConnectionRequestsByReceiverUser_IdAndAccepted(UUID receiverUserId, boolean accepted);

    Optional<ConnectionRequest> findConnectionRequestByIdAndReceiverUser_Id(UUID id, UUID receiverUserId);

    @Query("""
        SELECT cr FROM ConnectionRequest cr
        WHERE 
            (cr.senderUser.id = :currentUserId AND cr.receiverUser.id = :targetUserId) OR
            (cr.receiverUser.id = :currentUserId AND cr.senderUser.id = :targetUserId)
        """)
    Optional<ConnectionRequest> findConnectionBetween(
            @Param("currentUserId") UUID currentUserId,
            @Param("targetUserId") UUID targetUserId
    );
}
