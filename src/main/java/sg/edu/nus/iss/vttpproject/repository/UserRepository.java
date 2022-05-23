package sg.edu.nus.iss.vttpproject.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.vttpproject.model.Players;
import static sg.edu.nus.iss.vttpproject.repository.Queries.*;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate template;

    public List<Integer> getFavourites(String username) {
        List<Integer> ids = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_FAV, username);
        while (rs.next()) {
            Integer id = rs.getInt("player_id");
            ids.add(id);
        }
        return ids;
    }

    public boolean addToFav(Integer id, String username) {
        
        if(!checkFav(id, username)){
            int added = template.update(SQL_INSERT_FAV, username, id);
            return true;
        }else{
            int deleted = template.update(SQL_DELETE_FAV, username, id);
            return false;
        }
       
        
    }

    public boolean checkFav(Integer id, String username){
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_CHECKFAV, username, id);
        while (rs.next()) {
            return true;
        }
        return false;
    }

    public boolean checkFavTeam(Integer id, String username) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_CHECKFAV_TEAMS, username, id);
        while (rs.next()) {
            return true;
        }
        return false;
    }

    public boolean addToFavTeam(Integer id, String username) {
        if(!checkFavTeam(id, username)){
            int added = template.update(SQL_INSERT_FAV_TEAMS, username, id);
            return true;
        }else{
            int deleted = template.update(SQL_DELETE_FAV_TEAMS, username, id);
            return false;
        }
    }

    public List<Integer> getFavouritesTeams(String username) {
        List<Integer> ids = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_FAV_TEAMS, username);
        while (rs.next()) {
            Integer id = rs.getInt("team_id");
            ids.add(id);
        }
        return ids;
    }

    public boolean deleteUsername(String username) {
        int deleted = template.update(SQL_DELETE_USER, username);
        return deleted > 0;
    }
    
}
