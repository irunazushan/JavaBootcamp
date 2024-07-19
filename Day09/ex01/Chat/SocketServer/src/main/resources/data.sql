DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
 );

CREATE TABLE messages (
   id BIGSERIAL PRIMARY KEY,
   sender BIGINT NOT NULL,
   message TEXT,
   sent_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_sender FOREIGN KEY(sender) REFERENCES users(id)
);

INSERT INTO users (name, password) VALUES ('Alice', 'password123');
INSERT INTO users (name, password) VALUES ('Bob', 'securepass');
INSERT INTO users (name, password) VALUES ('Charlie', 'qwerty');
INSERT INTO users (name, password) VALUES ('David', 'letmein');
INSERT INTO users (name, password) VALUES ('Eve', 'password321');

INSERT INTO messages (sender, message, sent_time) VALUES (1, 'Hello, how are you?', '2022-01-01 12:00:00');
INSERT INTO messages (sender, message, sent_time) VALUES (2, 'Meeting at 3 pm tomorrow', '2022-01-02 15:00:00');
INSERT INTO messages (sender, message, sent_time) VALUES (3, 'Dont forget to submit the report', '2022-01-03 09:00:00');
INSERT INTO messages (sender, message, sent_time) VALUES (2, 'Happy birthday!', '2022-01-04 00:00:00');
INSERT INTO messages (sender, message) VALUES (1, 'Lets catch up for coffee');

SELECT * FROM messages;
SELECT * FROM users;