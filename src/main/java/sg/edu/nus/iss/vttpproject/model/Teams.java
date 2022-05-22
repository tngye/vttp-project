package sg.edu.nus.iss.vttpproject.model;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue.ValueType;

public class Teams {
    private Integer id;
    private String name;
    private String nickname;
    private String code;
    private String city;
    private String logo;
    private String league;
    private TeamStats stats;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }


    public TeamStats getStats() {
        return stats;
    }

    public void setStats(TeamStats stats) {
        this.stats = stats;
    }

    public static Teams create(JsonObject obj) {
        Teams teams = new Teams();
        teams.setId(obj.getInt("id"));
        teams.setName(obj.getString("name"));
        teams.setNickname(obj.getString("nickname"));
        teams.setCode(obj.getString("code"));
        teams.setCity(obj.getString("city"));
        String logo = null;
        if (obj.get("logo").getValueType() != ValueType.NULL) {
            logo = (obj.getString("logo"));
        }
        String league = null;
        if (obj.getJsonObject("leagues").containsKey("standard")) {
            if (obj.getJsonObject("leagues").getJsonObject("standard").get("conference")
                    .getValueType() != ValueType.NULL
                    && obj.getJsonObject("leagues").getJsonObject("standard").get("division")
                            .getValueType() != ValueType.NULL) {
                league = obj.getJsonObject("leagues").getJsonObject("standard").getString("conference") + " "
                        + obj.getJsonObject("leagues").getJsonObject("standard").getString("division");
            }
        }
        teams.setLeague(league);
        teams.setLogo(logo);
        return teams;
    }

    public static Teams convert(SqlRowSet rs) {
        Teams team = new Teams();
        team.setId(rs.getInt("team_id"));
        team.setName(rs.getString("name"));
        team.setNickname(rs.getString("nickname"));
        team.setCode(rs.getString("code"));
        team.setCity(rs.getString("city"));
        team.setLogo(rs.getString("logo"));
        team.setLeague(rs.getString("league"));
        TeamStats stats = new TeamStats();
        stats = stats.convert(rs);
        team.setStats(stats);
        return team;
    }

}
