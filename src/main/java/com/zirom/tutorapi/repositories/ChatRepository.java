package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query("SELECT c FROM Chat c WHERE c.senderUser.id = :userId OR c.receiverUser.id = :userId")
    List<Chat> findAllByReceiverUserIdOrSenderUserId(UUID userId);
}
