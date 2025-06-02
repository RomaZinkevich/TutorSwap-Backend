package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.dtos.chat.ChatMessagesDto;
import com.zirom.tutorapi.domain.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query("SELECT c FROM Chat c WHERE c.senderUser.id = :userId OR c.receiverUser.id = :userId")
    List<Chat> findAllByReceiverUserIdOrSenderUserId(UUID userId);

    @Query("""
        SELECT c FROM Chat c
        WHERE 
            (c.senderUser.id = :user1 AND c.receiverUser.id = :user2) OR
            (c.receiverUser.id = :user1 AND c.senderUser.id = :user2)
        """)
    Optional<Chat> findChatBetween(
            @Param("user1") UUID user1,
            @Param("user2") UUID user2
    );
}
