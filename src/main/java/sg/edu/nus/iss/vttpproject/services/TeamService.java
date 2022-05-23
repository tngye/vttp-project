package sg.edu.nus.iss.vttpproject.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import sg.edu.nus.iss.vttpproject.model.TeamStats;
import sg.edu.nus.iss.vttpproject.model.Teams;
import sg.edu.nus.iss.vttpproject.repository.TeamRepository;
@Service
public class TeamService {

    @Autowired
    private TeamRepository tRepo;
    
    @Value("${rapid.api.key}")
    private String rapidApiKey;

    
    final String url = "https://api-nba-v1.p.rapidapi.com/teams";

    public List<Teams> getTeams() throws IOException{

        RequestEntity req = RequestEntity.get(url)
                                         .header("X-RapidAPI-Host", "api-nba-v1.p.rapidapi.com")
                                         .header("X-RapidAPI-Key", rapidApiKey)
                                         .build();

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

        JsonArray dataArr = data.getJsonArray("response");
        
        Teams teams = new Teams();
        List<Teams> teamsList = new ArrayList<>();

        for(int i=0; i <dataArr.size();i++){
            JsonObject obj = dataArr.getJsonObject(i);
            teams = Teams.create(obj);
            teamsList.add(teams);
        }
        
        return teamsList;
    }

    public boolean addTeams(List<Teams> teamsList) {
        return tRepo.addTeams(teamsList);
    }

    public List<Teams> getTeamsFromDb() {
        return tRepo.getTeams();
    }

    public TeamStats getStats(Integer id) throws IOException {

        String statsurl = UriComponentsBuilder.fromUriString(url)
                .path("/statistics/")
                .queryParam("id", id)
                .queryParam("season", 2021)
                .toUriString();

        RequestEntity req = RequestEntity.get(statsurl)
                                         .header("X-RapidAPI-Host", "api-nba-v1.p.rapidapi.com")
                                         .header("X-RapidAPI-Key", rapidApiKey)
                                         .build();

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

        JsonObject dataObj = data.getJsonArray("response").getJsonObject(0);

        TeamStats teamstats = new TeamStats();

        teamstats = TeamStats.create(dataObj);
        try{
            tRepo.saveStats(teamstats, id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return teamstats;
    }

    public Teams getTeam(Integer id) {
        return tRepo.getTeam(id);
    }

    public boolean deleteTeams(Integer id) {
        return tRepo.deleteTeams(id);
    }
}


