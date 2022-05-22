package sg.edu.nus.iss.vttpproject.repository;

public interface Queries {
    public static final String SQL_INSERT_TEAMS = 
    "insert into teams (team_id, name, nickname, code, city, logo, league) values (?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_SELECT_ALL_TEAMS =
    "select * from teams";

    public static final String SQL_SELECT_TEAMS_BY_ID =
    "select * from teams where team_id = ?";

    public static final String SQL_INSERT_USER = 
    "insert into users (username, password) values (?, sha1(?))";

    public static final String SQL_SELECT_USER = 
    "select * from users where username= ? and password= sha1(?)";

    public static final String SQL_INSERT_PLAYERS = 
    "insert into players (player_id, name, dob, country, startYear, height, weight, college, jerseyNo) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_INSERT_STATS = 
    "update players set assists = ?, blocks = ? , steals = ? , turnovers = ? , offensiveRebound = ? , defensiveRebound = ? , totalRebound = ? , points = ? , gamesPlay = ? , pFouls = ? where player_id = ?";

    public static final String SQL_SELECT_PLAYER = 
    "select * from players where player_id= ?";

    public static final String SQL_INSERT_FAV = 
    "insert into usersfavplayers (username, player_id) values (?, ?)";

    public static final String SQL_SELECT_CHECKFAV = 
    "select * from usersfavplayers where username=? and player_id= ?";
    
    public static final String SQL_DELETE_FAV = 
    "delete from usersfavplayers where username=? and player_id= ?";

    public static final String SQL_SELECT_ALL_FAV =
    "select * from usersfavplayers where username = ?";

    public static final String SQL_INSERT_FAV_TEAMS = 
    "insert into usersfavteams (username, team_id) values (?, ?)";

    public static final String SQL_SELECT_CHECKFAV_TEAMS = 
    "select * from usersfavteams where username=? and team_id= ?";
    
    public static final String SQL_DELETE_FAV_TEAMS = 
    "delete from usersfavteams where username=? and team_id= ?";

    public static final String SQL_SELECT_ALL_FAV_TEAMS =
    "select * from usersfavteams where username = ?";

}
