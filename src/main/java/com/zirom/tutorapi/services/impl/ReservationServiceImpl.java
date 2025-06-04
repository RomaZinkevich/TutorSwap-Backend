package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.ReservationDirection;
import com.zirom.tutorapi.domain.entities.lesson.Lesson;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;
import com.zirom.tutorapi.repositories.LessonRepository;
import com.zirom.tutorapi.repositories.ReservationRepository;
import com.zirom.tutorapi.services.AuthorizationService;
import com.zirom.tutorapi.services.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final AuthorizationService authorizationService;
    private final LessonRepository lessonRepository;

    @Override
    public List<Reservation> getReservations(UUID id, RequestState state, ReservationDirection direction) {
        if (direction == ReservationDirection.TEACHER)
            return reservationRepository.findAllByUser_IdAndState(id, state);
        else
            return reservationRepository.findAllByLearner_IdAndState(id, state);
    }

    @Override
    public Reservation changeReservation(UUID loggedInUserId, UUID reservationId, boolean isAccepted) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
        authorizationService.canChangeReservation(reservation, loggedInUserId);
        reservation.setState(isAccepted ? RequestState.ACCEPTED : RequestState.REJECTED);
        Reservation savedReservation = reservationRepository.save(reservation);
        if (isAccepted) {
            Lesson lesson = new Lesson();
            lesson.setTeacher(reservation.getUser());
            lesson.setLearner(reservation.getLearner());
            lesson.setTimeStart(reservation.getTimeStart());
            lesson.setTimeEnd(reservation.getTimeEnd());
            lesson.setGoogleMeetingUrl(createGoogleMeetingUrl());
            lessonRepository.save(lesson);
        }

        return savedReservation;
    }

    private String createGoogleMeetingUrl() {
        return "http://google.meeting.example.com";
    }
}
