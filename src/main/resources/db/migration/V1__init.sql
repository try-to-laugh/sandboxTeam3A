CREATE TABLE users
(
    id_user bigint NOT NULL,
    name character(64),
    surname character(64),
    mail character(64),
    username character(64),
    password character(64),
    PRIMARY KEY (id_user)
);