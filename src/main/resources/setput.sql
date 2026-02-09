CREATE TABLE IF NOT EXISTS tournament (
                                          tid SERIAL PRIMARY KEY,
                                          tournament_name TEXT,
                                          start_date TEXT,
                                          duration INTEGER
);

CREATE TABLE IF NOT EXISTS team (
                                    tid SERIAL PRIMARY KEY,
                                    team_name TEXT,
                                    trid INTEGER,
                                    FOREIGN KEY(trid) REFERENCES tournament(tid)
    );

CREATE TABLE IF NOT EXISTS player (
                                      pid SERIAL PRIMARY KEY,
                                      pname TEXT,
                                      pemail TEXT UNIQUE,
                                      tid INTEGER,
                                      FOREIGN KEY(tid) REFERENCES team(tid)
    );

CREATE TABLE IF NOT EXISTS match (
                                     mid SERIAL PRIMARY KEY,
                                     round INTEGER,
                                     tid INTEGER,
                                     FOREIGN KEY(tid) REFERENCES tournament(tid)
    );

CREATE TABLE IF NOT EXISTS team_match (
                                          tid INTEGER,
                                          mid INTEGER,
                                          points REAL,
                                          PRIMARY KEY(tid, mid),
    FOREIGN KEY(tid) REFERENCES team(tid),
    FOREIGN KEY(mid) REFERENCES match(mid)
    );