package sg.edu.nus.iss.vttpproject.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.vttpproject.model.TeamStats;
import sg.edu.nus.iss.vttpproject.model.Teams;

import static sg.edu.nus.iss.vttpproject.repository.Queries.*;

@Repository
public class TeamRepository {

    @Autowired
    private JdbcTemplate template;

    // public void addTeams(List<Teams> teamsList, List<String> eastleagues,
    // List<String> westleagues) {
    // int added;
    // for (Teams t : teamsList) {
    // if (t.getLeague() != null) {
    // if (eastleagues.contains(t.getLeague().replace(" ", ""))
    // || westleagues.contains(t.getLeague().replace(" ", ""))) {
    // added = template.update(SQL_INSERT_TEAMS, t.getId(), t.getName(),
    // t.getNickname(), t.getCode(),
    // t.getCity(), t.getLogo(), t.getLeague());
    // }
    // }
    // }
    // }

    public boolean addTeams(List<Teams> teamsList) {
        int added = 0;
        for (Teams t : teamsList) {

            added = template.update(SQL_INSERT_TEAMS, t.getId(), t.getName(), t.getNickname(), t.getCode(),
                    t.getCity(), t.getLogo(), t.getLeague());
        }
        return added > 0;
    }

    public List<Teams> getTeams() {
        List<Teams> teams = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_TEAMS);
        while (rs.next()) {
            Teams team = Teams.convert(rs);
            teams.add(team);
        }
        return teams;
    }

    public Teams getTeam(Integer id) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_TEAMS_BY_ID, id);
        while (rs.next()) {
            return Teams.convert(rs);
        }
        return null;
    }

    public boolean saveStats(TeamStats teamstats, Integer id) {
        int added = 0;
        added = template.update(SQL_INSERT_STATS_TEAM, teamstats.getGamesPlayed(), teamstats.getBiggestLead(),
                teamstats.getSecondChancePoints(), teamstats.getPointsOffTurnovers(), teamstats.getLongestRun(),
                teamstats.getPoints(), teamstats.getOffReb(), teamstats.getDefReb(), teamstats.getTotReb(),
                teamstats.getAssists(), teamstats.getpFouls(), teamstats.getSteals(),
                teamstats.getTurnovers(), teamstats.getBlocks(), id);

        return added > 0;
    }

    public boolean deleteTeams(Integer id) {
        int deleted = template.update(SQL_DELETE_TEAM, id);
        return deleted > 0;
    }

}
