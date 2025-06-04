package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;

import java.util.UUID;

public interface AuthorizationService {

    void hasAccessToChat(Chat chat, UUID userId);
    void hasAccessToConnectionRequest(ConnectionRequest connectionRequest, UUID userId);
    void canChangeReservation(Reservation reservation, UUID userId);
}
