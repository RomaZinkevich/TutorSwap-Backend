package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findAllByUser_IdAndState(UUID userId, RequestState state);

    List<Reservation> findAllByLearner_IdAndState(UUID learnerId, RequestState state);
}
