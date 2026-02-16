drop table if exists team_match;
drop table if exists match;
drop table if exists player;
drop table if exists team;
drop table if exists tournament;

create table if not exists tournament
(
    tid             SERIAL primary key,
    tournament_name TEXT,
    start_date      TEXT,
    duration        INTEGER
);
create table if not exists team
(
    tid       SERIAL primary key,
    team_name TEXT,
    trid      INTEGER,
    foreign key (trid) references tournament (tid)
);
create table if not exists player
(
    pid    SERIAL primary key,
    pname  TEXT,
    pemail TEXT unique,
    tid    INTEGER,
    foreign key (tid) references team (tid)
);
create table if not exists match
(
    mid   SERIAL primary key,
    round INTEGER,
    tid   INTEGER,
    foreign key (tid) references tournament (tid)
);
create table if not exists team_match
(
    tid    INTEGER,
    mid    INTEGER,
    points REAL,
    primary key (tid, mid),
    foreign key (tid) references team (tid),
    foreign key (mid) references match (mid)
);

insert into tournament (tournament_name, start_date, duration)
values ('Winter Cup 2024', '2024-01-15', 14),
       ('Sommer Liga Pro', '2024-06-01', 30),
       ('Charity Event', '2024-09-10', 2),
       ('eSports Major', '2024-11-20', 7),
       ('Regionale Meisterschaft', '2024-03-05', 5);
insert into team (team_name, trid)
values ('Die wilden Kerle', 1),
       ('FC Datenbank', 1),
       ('SQL Strikers', 2),
       ('Python Panthers', 2),
       ('Java Giants', 3);
insert into player (pname, pemail, tid)
values ('Max Mustermann', 'max@test.de', 1),
       ('Erika Musterfrau', 'erika@test.de', 1),
       ('John Doe', 'john.doe@example.com', 2),
       ('Jane Smith', 'jane.s@web.de', 3),
       ('Lukas Podolski', 'poldi@fussball.de', 4);
insert into match (round, tid)
values (1, 1),
       (2, 1),
       (1, 2),
       (1, 3),
       (1, 4);
insert into team_match (tid, mid, points)
values (1, 1, 3.0),
       (2, 1, 0.0),
       (3, 3, 1.5),
       (4, 3, 1.5),
       (5, 4, 3.0);