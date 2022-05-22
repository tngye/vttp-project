package sg.edu.nus.iss.vttpproject.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.vttpproject.model.Players;
import sg.edu.nus.iss.vttpproject.model.PlayersStats;

import static sg.edu.nus.iss.vttpproject.repository.Queries.*;

@Repository
public class PlayerRepository {

    @Autowired
    private JdbcTemplate template;

    public boolean savePlayers(List<Players> playersList) {
        int added = 0;
        for (Players p : playersList) {

            added = template.update(SQL_INSERT_PLAYERS, p.getId(), p.getName(), p.getDob(), p.getCountry(),
                    p.getStartYear(), p.getHeight(), p.getWeight(), p.getCollege(), p.getJerseyNo());
        }
        return added > 0;
    }

    public boolean saveStats(PlayersStats stats, Integer id) {
        int added = 0;
        added = template.update(SQL_INSERT_STATS, stats.getAssists(), stats.getBlocks(), stats.getSteals(),
                stats.getTurnover(), stats.getOffensiveRebound(), stats.getDefensiveRebound(),
                stats.getTotalRebound(), stats.getPoints(), stats.getGamesPlay(), stats.getpFouls(), id);

        return added > 0;
    }

    public Players getPlayer(Integer id) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_PLAYER, id);
        while (rs.next()) {
            return Players.convert(rs);
        }
        return null;
    }


}
