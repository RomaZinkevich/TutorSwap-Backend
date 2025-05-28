package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.dtos.user.UserConnectionDto;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllBySkillToTeach(Skill skillToTeach);

    List<User> findAllBySkillToLearn(Skill skillToLearn);

    List<User> findAllByIdIsNot(UUID id);


    @Query(value = """
        SELECT 
            v.user_id AS id,
            v.name,
            v.email,
            v.profile_image,
            v.description,
            v.university_name,
            v.want_to_learn_skill_id AS skill_to_learn_id,
            v.want_to_teach_skill_id AS skill_to_teach_id,
            v.want_to_learn_detail,
            v.want_to_teach_detail,
            v.accepted,
            v.connection_type,
            CASE 
                WHEN v.sender_id = :currentUserId THEN FALSE
                WHEN v.receiver_id = :currentUserId THEN TRUE
                ELSE NULL
            END AS is_sender
        FROM user_connection_dto_view v
        WHERE v.user_id = :targetUserId
          AND (
              v.sender_id = :currentUserId OR
              v.receiver_id = :currentUserId
          )
        LIMIT 1
    """, nativeQuery = true)
    Map<String, Object> rawUserConnection(
            @Param("currentUserId") UUID currentUserId,
            @Param("targetUserId") UUID targetUserId
    );
}
