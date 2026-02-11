create table IF not exists tournament
(
    tid
    SERIAL
    primary
    key,
    tournament_name
    TEXT,
    start_date
    TEXT,
    duration
    integer
);
create table IF not exists team
(
    tid
    SERIAL
    primary
    key,
    team_name
    TEXT,
    trid
    integer,
    foreign
    key
(
    trid
) references tournament
(
    tid
) );
create table IF not exists player
(
    pid
    SERIAL
    primary
    key,
    pname
    TEXT,
    pemail
    TEXT
    unique,
    tid
    integer,
    foreign
    key
(
    tid
) references team
(
    tid
) );
create table IF not exists match
(
    mid
    SERIAL
    primary
    key,
    round
    integer,
    tid
    integer,
    foreign
    key
(
    tid
) references tournament
(
    tid
) );
create table IF not exists team_match
(
    tid
    integer,
    mid
    integer,
    points
    real,
    primary
    key
(
    tid,
    mid
), foreign key
(
    tid
) references team
(
    tid
), foreign key
(
    mid
) references match
(
    mid
) );