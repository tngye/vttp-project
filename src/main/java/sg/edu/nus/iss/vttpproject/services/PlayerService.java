package sg.edu.nus.iss.vttpproject.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.vttpproject.model.Players;
import sg.edu.nus.iss.vttpproject.model.PlayersStats;
import sg.edu.nus.iss.vttpproject.repository.PlayerRepository;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository pRepo;

    @Value("${rapid.api.key}")
    private String rapidApiKey;

    // public List<Players> getPlayers(Integer id) throws IOException {
    //     final String baseUrl = "https://api-nba-v1.p.rapidapi.com/players";

    //     String url = UriComponentsBuilder.fromUriString(baseUrl)
    //             .queryParam("team", id)
    //             .queryParam("season", "2021")
    //             .toUriString();

    //     RequestEntity req = RequestEntity.get(url)
    //             .header("X-RapidAPI-Host", "api-nba-v1.p.rapidapi.com")
    //             .header("X-RapidAPI-Key", rapidApiKey)
    //             .build();

    //     RestTemplate template = new RestTemplate();
    //     ResponseEntity<String> resp = null;
    //     try {
    //         resp = template.exchange(req, String.class);
    //     } catch (Exception ex) {
    //         ex.printStackTrace();
    //     }

    //     JsonObject data = null;

    //     try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
    //         JsonReader reader = Json.createReader(is);
    //         data = reader.readObject();
    //     }

    //     JsonArray dataArr = data.getJsonArray("response");

    //     System.out.println(">>>>TeamsArr: " + dataArr);

    //     Players player = new Players();
    //     List<Players> playersList = new ArrayList<>();

    //     for (int i = 0; i < dataArr.size(); i++) {
    //         JsonObject obj = dataArr.getJsonObject(i);
    //         player = Players.create(obj);
    //         playersList.add(player);
    //     }

    //     return playersList;
    // }

    public List<Players> getPlayers() throws IOException {
        final String baseUrl = "https://data.nba.net/data/10s/prod/v1/";
        String year = "2021";

        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path(year)
                .path("/players.json")
                .toUriString();

        RequestEntity req = RequestEntity.get(url).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        try {
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObject data = null;

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            data = reader.readObject();
        }

        JsonArray dataArr = data.getJsonObject("league").getJsonArray("standard");

        // System.out.println(">>>>TeamsArr: " + dataArr);

        Players player = new Players();
        List<Players> playersList = new ArrayList<>();

        for (int i = 0; i < dataArr.size(); i++) {
            JsonObject obj = dataArr.getJsonObject(i);
            // PlayersStats stats = getStats(Integer.parseInt(obj.getString("personId")));
            // player = Players.create(obj, stats);
            player = Players.create(obj);
            playersList.add(player);
        }

        //first time to add into tables
        // pRepo.savePlayers(playersList);
        return playersList;
    }

    public PlayersStats getStats(Integer id) throws IOException {

        final String baseUrl = "https://data.nba.net/data/10s/prod/v1/";
        String year = "2021";

        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path(year + "/players/")
                .path(id.toString())
                .path("_profile.json")
                .toUriString();

        RequestEntity req = RequestEntity.get(url).build();

        System.out.println(">>>>url: "+ url);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        try {
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObject data = null;

        if(resp == null){
            return null;
        }

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            data = reader.readObject();
        }

        JsonObject dataObj = data.getJsonObject("league").getJsonObject("standard").getJsonObject("stats")
                .getJsonObject("careerSummary");

        // System.out.println(">>>>TeamsArr: " + dataArr);

        PlayersStats stats = new PlayersStats();
        stats = PlayersStats.create(dataObj);

        try{
            pRepo.saveStats(stats, id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return stats;

    }

    public Players getPlayer(Integer id) {
        Players stats = pRepo.getPlayer(id);
        return stats;
    }

   

}
