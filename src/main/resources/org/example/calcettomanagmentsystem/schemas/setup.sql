pragma foreign_keys = ON
;

CREATE TABLE IF NOT EXISTS tournament (
    tid INTEGER PRIMARY KEY,
    tournament_name TEXT NOT NULL,
    start_date TEXT,
    duration INTEGER NOT NULL,
    pre_round INTEGER NOT NULL DEFAULT 3,
    current_round INTEGER NOT NULL DEFAULT 0,
    max_team_size INTEGER DEFAULT 2
)
;

CREATE TABLE IF NOT EXISTS team (
    tid INTEGER PRIMARY KEY, team_name TEXT /* Temporary change, missing constraint | UNIQUE | */)
;

CREATE TABLE IF NOT EXISTS player (
    pid INTEGER PRIMARY KEY,
    pname TEXT,
    pemail TEXT, /* Temporary change, missing constraint | UNIQUE | */
    tid INTEGER,
    trid INTEGER,
    FOREIGN key (tid) REFERENCES team(tid) ON DELETE CASCADE,
    FOREIGN key (trid) REFERENCES tournament(tid) ON DELETE CASCADE
)
;

CREATE TABLE IF NOT EXISTS "match" (
    mid INTEGER PRIMARY KEY, round INTEGER, tid INTEGER, FOREIGN key (tid) REFERENCES tournament(tid) ON DELETE CASCADE)
;

CREATE TABLE IF NOT EXISTS team_match (
    tid INTEGER,
    mid INTEGER,
    points REAL DEFAULT (- 1.0),
    PRIMARY KEY (tid, mid),
    FOREIGN key (tid) REFERENCES team(tid) ON DELETE CASCADE,
    FOREIGN key (mid) REFERENCES "match"(mid) ON DELETE CASCADE
)
;