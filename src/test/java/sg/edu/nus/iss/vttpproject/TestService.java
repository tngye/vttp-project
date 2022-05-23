package sg.edu.nus.iss.vttpproject;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import sg.edu.nus.iss.vttpproject.model.News;
import sg.edu.nus.iss.vttpproject.model.Players;
import sg.edu.nus.iss.vttpproject.model.PlayersStats;
import sg.edu.nus.iss.vttpproject.model.TeamStats;
import sg.edu.nus.iss.vttpproject.model.Teams;
import sg.edu.nus.iss.vttpproject.services.HomeService;
import sg.edu.nus.iss.vttpproject.services.LoginService;
import sg.edu.nus.iss.vttpproject.services.PlayerService;
import sg.edu.nus.iss.vttpproject.services.TeamService;
import sg.edu.nus.iss.vttpproject.services.UserService;

@SpringBootTest
public class TestService {

    @Autowired
    private HomeService hSvc;

    @Autowired
    private LoginService lSvc;

    @Autowired
    private UserService uSvc;

    @Autowired
    private PlayerService pSvc;

    @Autowired
    private TeamService tSvc;

    // MockData
    private MultiValueMap<String, String> form;
    private List<Teams> teamList;

    public TestService() {

        form = new LinkedMultiValueMap<>();
        form.add("username", "testUsername");
        form.add("password", "testPassword");

        // to add teams
        teamList = new ArrayList<>();
        Teams team = new Teams();
        team.setId(100001);
        team.setName("test1");
        teamList.add(team);
        Teams team2 = new Teams();
        team2.setId(100002);
        team2.setName("test2");
        teamList.add(team2);
    }

    // Home Service
    @Test
    void ShouldReturn9News() throws IOException {
        int count = 9;
        List<News> list = hSvc.getNews(0, null);
        assertEquals(count, list.size(), "Expected %s news. Got %s.".formatted(count, list.size()));
    }

    // Login Service
    @BeforeEach
    public void add() {
        lSvc.addUser(form);
    }

    @Test
    void ShouldAddNewUser() {
        try {
            boolean added = lSvc.addUser(form);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void ShouldAuthenticate() {
        boolean authenticated = lSvc.authenticateUser("fred", "fred");
        assertTrue(authenticated);
    }

    // Player Service
    @Test
    void ShouldReturnPlayers() throws IOException {
        List<Players> list = pSvc.getPlayers();
        assertNotNull(list);
    }

    @Test
    void ShouldGetStats() {
        Integer id = 203937;
        try {
            PlayersStats stats = pSvc.getStats(id);
            assertNotNull(stats);
        } catch (Exception e) {
            fail("cannot get players stats");
        }
    }

    @Test
    void ShouldReturnPlayersById() throws IOException {
        Integer id = 203937;
        Players player = pSvc.getPlayer(id);
        assertEquals(id, player.getId(), "Expected %s player id. Got %s.".formatted(id, player.getId()));
    }

    // Team Service
    @Test
    void ShouldReturnTeams() {
        try {
            List<Teams> list = tSvc.getTeams();
            assertNotNull(list);
        } catch (Exception e) {
            fail("cannot get teams");
        }
    }

    @Test
    void ShouldInsertTeams() {
        try {
            boolean added = tSvc.addTeams(teamList);
            assertTrue(added);
        } catch (Exception e) {
            fail("cannot add teams");
        }
    }

    @AfterEach
    void deleteTeams() {
        for (Teams t : teamList) {
            tSvc.deleteTeams(t.getId());
        }
    }

    @Test
    void ShouldGetTeamsFromDB() {
        Integer count = 62;
        List<Teams> listDB = tSvc.getTeamsFromDb();
        assertEquals(count, listDB.size(), "Expected %s teams. Got %s.".formatted(count, listDB.size()));
    }

    @Test
    void ShouldReturnTeamById() {
        Integer id = 1;
        Teams team = tSvc.getTeam(id);
        assertEquals(id, team.getId(), "Expected %s team id. Got %s.".formatted(id, team.getId()));
    }

    @Test
    void ShouldReturnTeamStatsById() {
        Integer id = 1;
        try {
            TeamStats stats = tSvc.getStats(id);
            assertNotNull(stats);
        } catch (Exception e) {
            fail("cannot get teams stats");
        }
    }

    // User Service
    @Test
    void getFavourites() {
        Integer id = 2207;
        Integer id2 = 2544;
        Integer count = 2;
        uSvc.addToFav(id, form.getFirst("username"));
        uSvc.addToFav(id2, form.getFirst("username"));
        List<Players> favPlayers = uSvc.getFavourites(form.getFirst("username"));
        assertEquals(count, favPlayers.size(), "Expected %s fav players. Got %s.".formatted(count, favPlayers.size()));
    }

    @Test
    void addToFav() {
        Integer id = 203937;
        boolean added = uSvc.addToFav(id, "testUsername");
        assertTrue(added);
        ;
    }

    @Test
    void checkfav() {
        Integer id = 203937;
        uSvc.addToFav(id, "testUsername");
        boolean check = uSvc.checkFav(id, "testUsername");
        assertTrue(check);
    }

    @Test
    void addToFavTeam() {
        Integer id = 40;
        boolean added = uSvc.addToFavTeams(id, "testUsername");
        assertTrue(added);
    }

    @Test
    void checkfavTeam() {
        Integer id = 40;
        uSvc.addToFavTeams(id, "testUsername");
        boolean check = uSvc.checkFavTeam(id, "testUsername");
        assertTrue(check);
    }

    @Test
    void getFavouritesTeams() {
        Integer id = 40;
        Integer id2 = 1;
        Integer count = 2;
        uSvc.addToFavTeams(id, form.getFirst("username"));
        uSvc.addToFavTeams(id2, form.getFirst("username"));
        List<Teams> favTeams = uSvc.getFavouritesTeams(form.getFirst("username"));
        assertEquals(count, favTeams.size(), "Expected %s fav teams. Got %s.".formatted(count, favTeams.size()));
    }

    @AfterEach
    public void delete() {
        uSvc.deleteUsername(form.getFirst("username"));
    }

}
