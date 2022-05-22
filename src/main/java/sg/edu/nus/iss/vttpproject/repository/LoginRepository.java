package sg.edu.nus.iss.vttpproject.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import static sg.edu.nus.iss.vttpproject.repository.Queries.*;

@Repository
public class LoginRepository {

    @Autowired
    private JdbcTemplate template;

    public boolean addUser(MultiValueMap<String, String> form) {
        int added = template.update(SQL_INSERT_USER, form.getFirst("username"), form.getFirst("password"));
        return added > 0;
    }

    public boolean authenticate(String username, String password) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_USER, username, password);

        if (rs.next()){
            return true;
        }else{
            return false;
        }
    }
    
}
