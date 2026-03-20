pragma
foreign_keys = ON
;

PRAGMA
table_info(team);

DROP TABLE IF EXISTS team_match
;

DROP TABLE IF EXISTS "match"
;

DROP TABLE IF EXISTS player
;

DROP TABLE IF EXISTS team
;

DROP TABLE IF EXISTS tournament
;

CREATE TABLE IF NOT EXISTS tournament
		(tid
		 INTEGER
		 PRIMARY
		 KEY,
		 tournament_name
		 TEXT
		 NOT
		 NULL,
		 start_date
		 TEXT,
		 duration
		 INTEGER
		 NOT
		 NULL,
		 pre_round
		 INTEGER
		 NOT
		 NULL
		 DEFAULT
		 3,
		 current_round
		 INTEGER
		 NOT
		 NULL
		 DEFAULT
		 0,
		 max_team_size
		 INTEGER
		 DEFAULT
		 2)
;

CREATE TABLE IF NOT EXISTS team
		(tid
		 INTEGER
		 PRIMARY
		 KEY,
		 team_name
		 TEXT /* Temporary change, missing constraint | UNIQUE | */)
;

CREATE TABLE IF NOT EXISTS player
		(pid
		 INTEGER
		 PRIMARY
		 KEY,
		 pname
		 TEXT,
		 pemail
		 TEXT, /* Temporary change, missing constraint | UNIQUE | */
		 tid
		 INTEGER,
		 trid
		 INTEGER,
		 FOREIGN
		 key
		(
		 tid) REFERENCES team
		(tid) ON DELETE CASCADE, FOREIGN key
		(trid) REFERENCES tournament
		(tid)
		      ON DELETE CASCADE )
;

CREATE TABLE IF NOT EXISTS "match"
		(mid
		 INTEGER
		 PRIMARY
		 KEY,
		 round
		 INTEGER,
		 tid
		 INTEGER,
		 FOREIGN
		 key
		(
		 tid) REFERENCES tournament
		(tid) ON DELETE CASCADE )
;

CREATE TABLE IF NOT EXISTS team_match
		(tid
		 INTEGER,
		 mid
		 INTEGER,
		 points
		 REAL
		 DEFAULT
		(
		 -
		 1.0), PRIMARY KEY
		(tid,
		 mid), FOREIGN key
		(tid) REFERENCES team
		(tid) ON DELETE CASCADE, FOREIGN key
		(mid) REFERENCES "match"
		(mid)
		      ON DELETE CASCADE )
;

INSERT INTO tournament (tid,
                        tournament_name,
                        start_date,
                        duration,
                        pre_round,
                        current_round,
                        max_team_size)
VALUES (0, 'Winter Open 2024', '2024-01-15', 7, 2, 0, 2),
       (1, 'Winter Closen 2024', '2024-01-15', 7, 2, 0, 2),
       (2,
        'Spring Invitational',
        '2024-03-10',
        5,
        2,
        0,
        2),
       (3,
        'Summer Championship',
        '2024-06-20',
        14,
        2,
        0,
        2),
       (4, 'Fall Classic', '2024-09-05', 10, 2, 0, 2),
       (5,
        'Weekend Warrior Cup',
        '2025-07-04',
        2,
        1,
        0,
        2)
;

INSERT INTO team (tid, team_name)
VALUES (0, 'Cldf Knights'),
       (1, 'Cyber Knights'),
       (2, 'Data Wizards'),
       (3, 'SQL Stars'),
       (4, 'Byte Brawlers'),
       (5, 'Null Pointers'),
       (6, 'Query Queens'),
       (7, 'Logic Bombs'),
       (8, 'Bit Flippers'),
       (9, 'Cloud Runners'),
       (10, 'Alpha Testers')
;

INSERT INTO player (pid, pname, pemail, tid, trid)
VALUES (0, 'Alice Der Neger', 'alice@example.com', 1, 1),
       (1, 'Alice Smith', 'alice@example.com', 1, 1),
       (2, 'Bob Jones', 'bob@example.com', 1, 1),
       (3, 'Charlie Brown', 'charlie@example.com', 2, 1),
       (4, 'Diana Prince', 'diana@example.com', 2, 1),
       (5, 'Edward Norton', 'edward@example.com', 3, 2),
       (6, 'Fiona Gallagher', 'fiona@example.com', 3, 2),
       (7, 'George Miller', 'george@example.com', 4, 2),
       (8, 'Hannah Abbott', 'hannah@example.com', 4, 2),
       (9, 'Ian Wright', 'ian@example.com', 5, 3),
       (10, 'Jenny Slate', 'jenny@example.com', 5, 3),
       (11, 'Kevin Hart', 'kevin@example.com', 6, 3),
       (12, 'Laura Palmer', 'laura@example.com', 6, 3),
       (13, 'Mike Ross', 'mike@example.com', 7, 4),
       (14, 'Nina Simone', 'nina@example.com', 7, 4),
       (15, 'Oscar Wilde', 'oscar@example.com', 8, 4),
       (16, 'Peter Parker', 'peter@example.com', 8, 4),
       (17, 'Quinn Fabray', 'quinn@example.com', 9, 5),
       (18, 'Riley Reid', 'riley@example.com', 9, 5),
       (19, 'Steve Rogers', 'steve@example.com', 10, 5),
       (20, 'Tony Stark', 'tony@example.com', 10, 5)
;

INSERT INTO "match" (mid, round, tid)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 1, 2),
       (4, 2, 2),
       (5, 1, 3),
       (6, 2, 3),
       (7, 1, 4),
       (8, 2, 4),
       (9, 1, 5)
;

INSERT INTO team_match (tid, mid, points)
VALUES (1, 1, 10.5),
       (2, 1, 8.0),
       (1, 2, 12.0),
       (2, 2, 14.5),
       (3, 3, 5.0),
       (4, 3, 7.5),
       (3, 4, 15.0),
       (4, 4, 12.5),
       (5, 5, 9.0),
       (6, 5, 9.5),
       (5, 6, 20.0),
       (6, 6, 18.0),
       (7, 7, 11.0),
       (8, 7, 10.0),
       (7, 8, 21.0),
       (8, 8, 19.0),
       (9, 9, 13.0),
       (10, 9, 12.0)
;
INSERT INTO tournament (tid, tournament_name, start_date, duration, pre_round, current_round, max_team_size)
VALUES (6, 'Pro League 2026', '2026-06-01', 14, 3, 0, 2);

INSERT INTO team (tid, team_name)
VALUES (11, 'Code Cobras'),
       (12, 'Bit Bandits'),
       (13, 'Pixel Paladins'),
       (14, 'Script Spartans'),
       (15, 'Bug Hunters'),
       (16, 'Cache Cowboys'),
       (17, 'Kernel Kings'),
       (18, 'Data Ducks'),
       (19, 'Boolean Bears'),
       (20, 'Array Avengers'),
       (21, 'Git Guardians'),
       (22, 'Linker Lions'),
       (23, 'Heap Heroes'),
       (24, 'Stack Stormers'),
       (25, 'Macro Masters'),
       (26, 'Thread Titans');

/*
INSERT INTO player (pid, pname, pemail, tid, trid)
VALUES (21, 'Max Muster', 'max@code.com', 11, 0),
    (22, 'Moritz Muster', 'moritz@code.com', 11, 0),
    (23, 'Tim Test', 'tim@bit.com', 12, 0),
    (24, 'Tom Test', 'tom@bit.com', 12, 0),
    (25, 'Paul Pixel', 'paul@pixel.com', 13, 0),
    (26, 'Paula Pixel', 'paula@pixel.com', 13, 0),
    (27, 'Sam Script', 'sam@script.com', 14, 0),
    (28, 'Sara Script', 'sara@script.com', 14, 0),
    (29, 'Ben Bug', 'ben@bug.com', 15, 0),
    (30, 'Bella Bug', 'bella@bug.com', 15, 0),
    (31, 'Chris Cache', 'chris@cache.com', 10, 0),
    (32, 'Cora Cache', 'cora@cache.com', 10, 0),
    (33, 'Karl Kernel', 'karl@kernel.com', 17, 0),
    (34, 'Kyra Kernel', 'kyra@kernel.com', 17, 0),
    (35, 'Dan Data', 'dan@data.com', 18, 0),
    (36, 'Dora Data', 'dora@data.com', 18, 0),
    (37, 'Bert Boolean', 'bert@bool.com', 19, 0),
    (38, 'Beate Boolean', 'beate@bool.com', 19, 0),
    (39, 'Adam Array', 'adam@array.com', 20, 0),
    (40, 'Abby Array', 'abby@array.com', 20, 0),
    (41, 'Gerry Git', 'gerry@git.com', 21, 0),
    (42, 'Gabi Git', 'gabi@git.com', 21, 0),
    (43, 'Leo Linker', 'leo@link.com', 22, 0),
    (44, 'Lina Linker', 'lina@link.com', 22, 0),
    (45, 'Harry Heap', 'harry@heap.com', 23, 0),
    (46, 'Hanni Heap', 'hanni@heap.com', 23, 0),
    (47, 'Stan Stack', 'stan@stack.com', 24, 0),
    (48, 'Stella Stack', 'stella@stack.com', 24, 0),
    (49, 'Marc Macro', 'marc@macro.com', 25, 0),
    (50, 'Mina Macro', 'mina@macro.com', 25, 0),
    (51, 'Theo Thread', 'theo@thread.com', 20, 0),
    (52, 'Thea Thread', 'thea@thread.com', 20, 0);
*/

INSERT INTO player (pid, pname, pemail, trid)
VALUES (21, 'Max Muster', 'max@code.com', 0),
       (22, 'Moritz Muster', 'moritz@code.com', 0),
       (23, 'Tim Test', 'tim@bit.com', 0),
       (24, 'Tom Test', 'tom@bit.com', 0),
       (25, 'Paul Pixel', 'paul@pixel.com', 0),
       (26, 'Paula Pixel', 'paula@pixel.com', 0),
       (27, 'Sam Script', 'sam@script.com', 0),
       (28, 'Sara Script', 'sara@script.com', 0),
       (29, 'Ben Bug', 'ben@bug.com', 0),
       (30, 'Bella Bug', 'bella@bug.com', 0),
       (31, 'Chris Cache', 'chris@cache.com', 0),
       (32, 'Cora Cache', 'cora@cache.com', 0),
       (33, 'Karl Kernel', 'karl@kernel.com', 0),
       (34, 'Kyra Kernel', 'kyra@kernel.com', 0),
       (35, 'Dan Data', 'dan@data.com', 0),
       (36, 'Dora Data', 'dora@data.com', 0),
       (37, 'Bert Boolean', 'bert@bool.com', 0),
       (38, 'Beate Boolean', 'beate@bool.com', 0),
       (39, 'Adam Array', 'adam@array.com', 0),
       (40, 'Abby Array', 'abby@array.com', 0),
       (41, 'Gerry Git', 'gerry@git.com', 0),
       (42, 'Gabi Git', 'gabi@git.com', 0),
       (43, 'Leo Linker', 'leo@link.com', 0),
       (44, 'Lina Linker', 'lina@link.com', 0),
       (45, 'Harry Heap', 'harry@heap.com', 0),
       (46, 'Hanni Heap', 'hanni@heap.com', 0),
       (47, 'Stan Stack', 'stan@stack.com', 0),
       (48, 'Stella Stack', 'stella@stack.com', 0),
       (49, 'Marc Macro', 'marc@macro.com', 0),
       (50, 'Mina Macro', 'mina@macro.com', 0),
       (51, 'Theo Thread', 'theo@thread.com', 0),
       (52, 'Thea Thread', 'thea@thread.com', 0);