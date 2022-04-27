INSERT INTO role (role_id, name) values (1, 'admin');
INSERT INTO role (role_id, name) values (2, 'user');

INSERT INTO usr(usr_id, role_id, username, password, email, firstname, lastname, birthday) values(1, 1, 'admin', '$2a$08$BgRiuJpAz/wOVKRZm/YVs.XtiineYwd/HnleY0HCe5oleDJb8nEN2', 'admin@gmail.com', 'Peter', 'Petrov', '2003-08-30');
INSERT INTO usr(usr_id, role_id, username, password, email, firstname, lastname, birthday) values(2, 2, 'user', '$2a$08$1z9V61dZ2oto6B5LhqyFUupZbvem27Ji3a5mBHrj5hM9C3nk/LZY6', 'user@gmail.com', 'Alexandr', 'Alexandrov', '2003-12-12');