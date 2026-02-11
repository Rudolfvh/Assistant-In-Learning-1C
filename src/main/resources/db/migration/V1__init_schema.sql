CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE topics (
                        id BIGSERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        description VARCHAR(1000)
);

CREATE TABLE lessons (
                         id BIGSERIAL PRIMARY KEY,
                         topic_id BIGINT UNIQUE,
                         content VARCHAR(5000),
                         code_example VARCHAR(3000),
                         CONSTRAINT fk_lesson_topic
                             FOREIGN KEY (topic_id)
                                 REFERENCES topics(id)
);

CREATE TABLE questions (
                           id BIGSERIAL PRIMARY KEY,
                           topic_id BIGINT,
                           text VARCHAR(1000),
                           CONSTRAINT fk_question_topic
                               FOREIGN KEY (topic_id)
                                   REFERENCES topics(id)
);

CREATE TABLE answers (
                         id BIGSERIAL PRIMARY KEY,
                         question_id BIGINT,
                         text VARCHAR(1000),
                         correct BOOLEAN,
                         CONSTRAINT fk_answer_question
                             FOREIGN KEY (question_id)
                                 REFERENCES questions(id)
);

CREATE TABLE user_progress (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT,
                               topic_id BIGINT,
                               score INT,
                               completed BOOLEAN
);
