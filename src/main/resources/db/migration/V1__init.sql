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

INSERT INTO users (id_user, name, surname, mail, username, password) VALUES(1, 'Stepan', 'Nedotepan', 'stepa@gmail.com',
'realStepan', '1111');

INSERT INTO users (id_user, name, surname, mail, username, password) VALUES(2, 'Pavel', 'Java', 'paja@gmail.com',
'paja', '2222');