-- Skills table (referenced by users)
CREATE TABLE skills (
                        id uuid NOT NULL,
                        name character varying(255) NOT NULL,
                        CONSTRAINT skills_pkey PRIMARY KEY (id),
                        CONSTRAINT uk85woe63nu9klkk9fa73vf0jd0 UNIQUE (name)
);

-- Users table
CREATE TABLE users (
                       id uuid NOT NULL,
                       description character varying(255) NOT NULL,
                       email character varying(255) NOT NULL,
                       name character varying(255) NOT NULL,
                       profile_image character varying(255) NOT NULL,
                       role character varying(255) NOT NULL,
                       university_name character varying(255) NOT NULL,
                       want_to_learn_detail character varying(255) NOT NULL,
                       want_to_teach_detail character varying(255) NOT NULL,
                       want_to_learn_skill_id uuid NOT NULL,
                       want_to_teach_skill_id uuid NOT NULL,
                       CONSTRAINT users_pkey PRIMARY KEY (id),
                       CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
                       CONSTRAINT users_role_check CHECK (role IN ('USER', 'ADMIN')),
                       CONSTRAINT fkd3w8li87xb5hfm7qpx1dp1xdd FOREIGN KEY (want_to_learn_skill_id) REFERENCES skills(id),
                       CONSTRAINT fkd2inf5avpd5w65l2a0mbbc941 FOREIGN KEY (want_to_teach_skill_id) REFERENCES skills(id)
);

-- Chats table
CREATE TABLE chats (
                       id uuid NOT NULL,
                       receiver_id uuid NOT NULL,
                       sender_id uuid NOT NULL,
                       CONSTRAINT chats_pkey PRIMARY KEY (id),
                       CONSTRAINT fk6dbye15iemw6gjqt0q4q06nf1 FOREIGN KEY (receiver_id) REFERENCES users(id),
                       CONSTRAINT fkla7peq6fislsxok7a4wxv5p36 FOREIGN KEY (sender_id) REFERENCES users(id)
);

-- Connection requests table
CREATE TABLE connection_requests (
                                     id uuid NOT NULL,
                                     created_at timestamp(6) without time zone NOT NULL,
                                     message_content character varying(255) NOT NULL,
                                     request_state smallint NOT NULL,
                                     receiver_id uuid NOT NULL,
                                     sender_id uuid NOT NULL,
                                     CONSTRAINT connection_requests_pkey PRIMARY KEY (id),
                                     CONSTRAINT connection_requests_check CHECK (sender_id <> receiver_id),
                                     CONSTRAINT connection_requests_request_state_check CHECK (request_state >= 0 AND request_state <= 2),
                                     CONSTRAINT ukonq4o0of70astiq7mo06nn6gs UNIQUE (sender_id, receiver_id),
                                     CONSTRAINT fkj291paj3vuf40hnbggyqc4fr5 FOREIGN KEY (sender_id) REFERENCES users(id),
                                     CONSTRAINT fkrbqpgx91y8jhk8glwkv33mqxu FOREIGN KEY (receiver_id) REFERENCES users(id)
);

-- Connections table
CREATE TABLE connections (
                             id uuid NOT NULL,
                             partner_id uuid NOT NULL,
                             user_id uuid NOT NULL,
                             CONSTRAINT connections_pkey PRIMARY KEY (id),
                             CONSTRAINT connections_check CHECK (partner_id <> user_id),
                             CONSTRAINT ukkdh7082kj30ankf6e8y5rxujs UNIQUE (user_id, partner_id),
                             CONSTRAINT fk8pokwk0ci096wu81yyporkm84 FOREIGN KEY (partner_id) REFERENCES users(id),
                             CONSTRAINT fkltpo1ymtaafd67hx5tls1db6u FOREIGN KEY (user_id) REFERENCES users(id)
);

-- User durations table
CREATE TABLE durations (
                           id uuid NOT NULL,
                           duration integer NOT NULL,
                           user_id uuid NOT NULL,
                           CONSTRAINT durations_pkey PRIMARY KEY (id),
                           CONSTRAINT fk_duration_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- User preferences table
CREATE TABLE preferences (
                             id uuid NOT NULL,
                             future_days integer NOT NULL,
                             min_notice_hours double precision NOT NULL,
                             user_id uuid NOT NULL,
                             CONSTRAINT preferences_pkey PRIMARY KEY (id),
                             CONSTRAINT fk_preference_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- OAuth providers table
CREATE TABLE providers (
                           id uuid NOT NULL,
                           name character varying(255) NOT NULL,
                           provider_user_id character varying(255) NOT NULL,
                           user_id uuid NOT NULL,
                           CONSTRAINT providers_pkey PRIMARY KEY (id),
                           CONSTRAINT ukeidk2ofdb0vfviyp2p5yorfne UNIQUE (provider_user_id),
                           CONSTRAINT fk_provider_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- User schedules table
CREATE TABLE schedules (
                           id uuid NOT NULL,
                           day_of_week smallint NOT NULL,
                           end_time timestamp(6) without time zone NOT NULL,
                           start_time timestamp(6) without time zone NOT NULL,
                           user_id uuid NOT NULL,
                           CONSTRAINT schedules_pkey PRIMARY KEY (id),
                           CONSTRAINT schedules_day_of_week_check CHECK (day_of_week >= 0 AND day_of_week <= 6),
                           CONSTRAINT fk_schedule_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Reservations table
CREATE TABLE reservations (
                              id uuid NOT NULL,
                              date timestamp(6) without time zone NOT NULL,
                              state smallint NOT NULL,
                              time_end timestamp(6) without time zone NOT NULL,
                              time_start timestamp(6) without time zone NOT NULL,
                              learner_id uuid NOT NULL,
                              user_id uuid NOT NULL,
                              CONSTRAINT reservations_pkey PRIMARY KEY (id),
                              CONSTRAINT reservations_state_check CHECK (state >= 0 AND state <= 2),
                              CONSTRAINT fk_reservation_learner FOREIGN KEY (learner_id) REFERENCES users(id) ON DELETE CASCADE,
                              CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Lessons table
CREATE TABLE lessons (
                         id uuid NOT NULL,
                         google_meeting_url character varying(255) NOT NULL,
                         time_end timestamp(6) without time zone NOT NULL,
                         time_start timestamp(6) without time zone NOT NULL,
                         learner_id uuid NOT NULL,
                         teacher_id uuid NOT NULL,
                         CONSTRAINT lessons_pkey PRIMARY KEY (id),
                         CONSTRAINT fk_lesson_learner FOREIGN KEY (learner_id) REFERENCES users(id) ON DELETE CASCADE,
                         CONSTRAINT fk_lesson_teacher FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Messages table (base table for polymorphic messages)
CREATE TABLE messages (
                          id uuid NOT NULL,
                          message_type character varying(255) NOT NULL,
                          "timestamp" timestamp(6) without time zone NOT NULL,
                          chat_id uuid NOT NULL,
                          receiver_id uuid NOT NULL,
                          sender_id uuid NOT NULL,
                          CONSTRAINT messages_pkey PRIMARY KEY (id),
                          CONSTRAINT messages_message_type_check CHECK (message_type IN ('IMAGE', 'VIDEO', 'TEXT', 'SCHEDULE', 'LESSON')),
                          CONSTRAINT fk_message_chat FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
                          CONSTRAINT fk4ui4nnwntodh6wjvck53dbk9m FOREIGN KEY (sender_id) REFERENCES users(id),
                          CONSTRAINT fkt05r0b6n0iis8u7dfna4xdh73 FOREIGN KEY (receiver_id) REFERENCES users(id)
);

-- Text messages table
CREATE TABLE textmessages (
                              message_id uuid NOT NULL,
                              content character varying(255),
                              CONSTRAINT textmessages_pkey PRIMARY KEY (message_id),
                              CONSTRAINT fk_textmessage_basemessage FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE
);

-- Image messages table
CREATE TABLE imagemessages (
                               message_id uuid NOT NULL,
                               caption character varying(255) NOT NULL,
                               image_url character varying(255) NOT NULL,
                               CONSTRAINT imagemessages_pkey PRIMARY KEY (message_id),
                               CONSTRAINT fk_imagemessage_basemessage FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE
);

-- Video messages table
CREATE TABLE videomessages (
                               message_id uuid NOT NULL,
                               caption character varying(255) NOT NULL,
                               video_url character varying(255) NOT NULL,
                               CONSTRAINT videomessages_pkey PRIMARY KEY (message_id),
                               CONSTRAINT fk_videomessage_basemessage FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE
);

-- Schedule messages table
CREATE TABLE schedulemessages (
                                  message_id uuid NOT NULL,
                                  CONSTRAINT schedulemessages_pkey PRIMARY KEY (message_id),
                                  CONSTRAINT fk_schedulemessage_basemessage FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE
);

-- Lesson request messages table
CREATE TABLE lessonrequestmessages (
                                       message_id uuid NOT NULL,
                                       description character varying(255) NOT NULL,
                                       time_end timestamp(6) without time zone NOT NULL,
                                       time_start timestamp(6) without time zone NOT NULL,
                                       reservation_id uuid NOT NULL,
                                       CONSTRAINT lessonrequestmessages_pkey PRIMARY KEY (message_id),
                                       CONSTRAINT fk_lessonrequestmessage_basemessage FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE,
                                       CONSTRAINT fk_lessonmessage_reservation FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
);