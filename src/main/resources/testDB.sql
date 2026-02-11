DROP TABLE IF EXISTS team_match;
DROP TABLE IF EXISTS match;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS tournament;

CREATE TABLE IF NOT EXISTS tournament
(
		tid
		SERIAL
		PRIMARY
		KEY,
		tournament_name
		TEXT,
		start_date
		TEXT,
		duration
		INTEGER
);

CREATE TABLE IF NOT EXISTS team
(
		tid
		SERIAL
		PRIMARY
		KEY,
		team_name
		TEXT,
		trid
		INTEGER,
		FOREIGN
		KEY
(
		trid
) REFERENCES tournament
(
		tid
) );

CREATE TABLE IF NOT EXISTS player
(
		pid
		SERIAL
		PRIMARY
		KEY,
		pname
		TEXT,
		pemail
		TEXT
		UNIQUE,
		tid
		INTEGER,
		FOREIGN
		KEY
(
		tid
) REFERENCES team
(
		tid
) );

CREATE TABLE IF NOT EXISTS match
(
		mid
		SERIAL
		PRIMARY
		KEY,
		round
		INTEGER,
		tid
		INTEGER,
		FOREIGN
		KEY
(
		tid
) REFERENCES tournament
(
		tid
) );

CREATE TABLE IF NOT EXISTS team_match
(
		tid
		INTEGER,
		mid
		INTEGER,
		points
		REAL,
		PRIMARY
		KEY
(
		tid,
		mid
), FOREIGN KEY
(
		tid
) REFERENCES team
(
		tid
), FOREIGN KEY
(
		mid
) REFERENCES match
(
		mid
) );


INSERT INTO tournament (tournament_name, start_date, duration)
VALUES ('Winter Cup 2024', '2024-01-15', 14),
       ('Sommer Liga Pro', '2024-06-01', 30),
       ('Charity Event', '2024-09-10', 2),
       ('eSports Major', '2024-11-20', 7),
       ('Regionale Meisterschaft', '2024-03-05', 5);


INSERT INTO team (team_name, trid)
VALUES ('Die wilden Kerle', 1),
       ('FC Datenbank', 1),
       ('SQL Strikers', 2),
       ('Python Panthers', 2),
       ('Java Giants', 3);


INSERT INTO player (pname, pemail, tid)
VALUES ('Max Mustermann', 'max@test.de', 1),
       ('Erika Musterfrau', 'erika@test.de', 1),
       ('John Doe', 'john.doe@example.com', 2),
       ('Jane Smith', 'jane.s@web.de', 3),
       ('Lukas Podolski', 'poldi@fussball.de', 4);


INSERT INTO match (round, tid)
VALUES (1, 1),
       (2, 1),
       (1, 2),
       (1, 3),
       (1, 4);


INSERT INTO team_match (tid, mid, points)
VALUES (1, 1, 3.0),
       (2, 1, 0.0),
       (3, 3, 1.5),
       (4, 3, 1.5),
       (5, 4, 3.0);

