package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
