PRAGMA
foreign_keys = on;

create table IF not exists tournament
(
		tid
		integer
		primary
		key,
		tournament_name
		TEXT
		not
		null,
		start_date
		TEXT,
		duration
		integer
		not
		null,
		pre_round
		integer
		not
		null,
		current_round
		integer
		not
		null,
		max_team_size
		integer
		default
(
		2
) );

create table IF not exists team
(
		tid
		integer
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
		integer
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

create table IF not exists "match"
( -- Wrapped in quotes because 'match' is a keyword
		mid
		integer
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
) references "match"
(
		mid
) );