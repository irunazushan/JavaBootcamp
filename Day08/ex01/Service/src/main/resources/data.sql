DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE
 );

INSERT INTO users (email)
VALUES ('email1@mail.com'),
    ('email2@gmail.com'),
    ('email3@mail.ru'),
    ('email4@mail.org'),
    ('email5@mail.edu');