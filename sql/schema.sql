-- drop database if exists
drop schema if exists nba;

-- create a new database
create schema nba;

-- select database
use nba;

-- create table
create table teams (
    team_id int not null,
    name varchar(128) not null,
    nickname varchar(128),
    code varchar(16), 
    city varchar(16), 
    logo varchar(2083), 
    league varchar(256),
    gamesPlayed int,
    biggestLead int,
    secondChancePoints int,
    pointsOffTurnovers int,
    longestRun int,
    points int,
    offReb int,
    defReb int,
    totReb int,
    assists int,
    pFouls int,
    steals int,
    turnovers int,
    blocks int,
    
    primary key(team_id)
);

-- create table
create table users(
    username varchar(32) not null,
    password varchar(256) not null,

    primary key (username)
);

-- create table
create table players(
    player_id int not null,
    name varchar(128) not null,
    dob Date,
    country varchar(256),
    college varchar(256),
    startYear int, 
    height decimal,
    weight decimal,
    jerseyNo int,
    assists int,
    blocks int,
    steals int,
    turnovers int,
    offensiveRebound int,
    defensiveRebound int,
    totalRebound int,
    points int,
    gamesPlay int,
    pFouls int,

    primary key (player_id)
);

--create table
create table usersfavplayers(
    id int not null auto_increment,
    username varchar(32),
    player_id int,

    primary key (id),

    constraint fk_player_id
        foreign key(player_id)
        references players(player_id)
);

--create table
create table usersfavteams(
    id int not null auto_increment,
    username varchar(32),
    team_id int,

    primary key (id),

    constraint fk_team_id
        foreign key(team_id)
        references teams(team_id)
);
