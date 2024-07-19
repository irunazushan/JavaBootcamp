INSERT INTO users (login, password) VALUES ('user1', 'password1');
INSERT INTO users (login, password) VALUES ('user2', 'password2');
INSERT INTO users (login, password) VALUES ('user3', 'password3');
INSERT INTO users (login, password) VALUES ('user4', 'password4');
INSERT INTO users (login, password) VALUES ('user5', 'password5');

INSERT INTO chatrooms (name, owner) VALUES ('Room1', 1);
INSERT INTO chatrooms (name, owner) VALUES ('Room2', 2);
INSERT INTO chatrooms (name, owner) VALUES ('Room3', 3);
INSERT INTO chatrooms (name, owner) VALUES ('Room4', 4);
INSERT INTO chatrooms (name, owner) VALUES ('Room5', 5);

INSERT INTO messages (author, room, message, date_time) VALUES (1, 1, 'Hello from user1 in Room1', '2024-07-01 10:00:00');
INSERT INTO messages (author, room, message, date_time) VALUES (2, 1, 'Hello from user2 in Room1', '2024-07-01 10:05:00');
INSERT INTO messages (author, room, message, date_time) VALUES (3, 2, 'Hello from user3 in Room2', '2024-07-01 10:10:00');
INSERT INTO messages (author, room, message, date_time) VALUES (4, 2, 'Hello from user4 in Room2', '2024-07-01 10:15:00');
INSERT INTO messages (author, room, message, date_time) VALUES (5, 3, 'Hello from user5 in Room3', '2024-07-01 10:20:00');