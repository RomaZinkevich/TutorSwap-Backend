package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllBySkillToTeach(Skill skillToTeach);

    List<User> findAllBySkillToLearn(Skill skillToLearn);

    List<User> findAllByIdIsNot(UUID id);
}
