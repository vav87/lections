CREATE TABLE messages
(
    "id"      BIGSERIAL,
    "user_id" BIGINT NOT NULL,
    "text"    TEXT NOT NULL
);

CREATE TABLE messages_users
(
    "user_id"    BIGINT NOT NULL,
    "message_id" BIGINT NOT NULL
);

CREATE TABLE users
(
    "id"   BIGSERIAL,
    "name" TEXT NOT NULL
);

