package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, UUID> {
    List<ConnectionRequest> findConnectionRequestsByReceiverUser_IdAndAccepted(UUID receiverUserId, boolean accepted);

    Optional<ConnectionRequest> findConnectionRequestByIdAndReceiverUser_Id(UUID id, UUID receiverUserId);
}
