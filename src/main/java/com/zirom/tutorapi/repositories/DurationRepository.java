package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.availability.Duration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DurationRepository extends JpaRepository<Duration, UUID> {
}
