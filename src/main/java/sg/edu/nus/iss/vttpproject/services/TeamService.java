package sg.edu.nus.iss.vttpproject.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
import sg.edu.nus.iss.vttpproject.model.Teams;
@Service
public class TeamService {
    
    @Value("${rapid.api.key}")
    private String rapidApiKey;

    public List<Teams> getTeams() throws IOException{
        final String url = "https://api-nba-v1.p.rapidapi.com/teams";

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

        System.out.println(">>>>TeamsArr: " + dataArr);
        
        Teams teams = new Teams();
        List<Teams> teamsList = new ArrayList<>();

        for(int i=0; i <dataArr.size();i++){
            JsonObject obj = dataArr.getJsonObject(i);
            teams = Teams.create(obj);
            teamsList.add(teams);
            System.out.println(">>>img: " + obj.get("logo").toString());
        }
        
        return teamsList;
    }
}


