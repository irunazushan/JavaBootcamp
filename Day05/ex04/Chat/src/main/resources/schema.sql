DROP TABLE IF EXISTS users, chatrooms, messages;

CREATE TABLE users
(
  id BIGSERIAL PRIMARY KEY,
  login VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
 );

CREATE TABLE chatrooms
(
  id BIGSERIAL primary key ,
  name VARCHAR(255) not NULL,
  owner BIGINT,
  CONSTRAINT fk_owner FOREIGN KEY(owner) REFERENCES users(id)
  );

CREATE TABLE messages
(
  id BIGSERIAL PRIMARY KEY,
  author BIGINT,
  room BIGINT,
  message TEXT,
  date_time TIMESTAMP,
  CONSTRAINT fk_author FOREIGN KEY(author) REFERENCES users(id),
  CONSTRAINT fk_room FOREIGN KEY(room) REFERENCES chatrooms(id)
  );