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
import jakarta.json.JsonValue.ValueType;
import sg.edu.nus.iss.vttpproject.model.News;

@Service
public class HomeService {
    
    @Value("${news.api.key}")
    private String newsApiKey;

    List<News> newsList = new ArrayList<>();

    
    public Integer pageNumber;
    
    public List<News> getNews(Integer page) throws IOException{
        pageNumber = page;
        final String Url = "https://newsdata.io/api/1/news";
        final String keyword = "nba"; 
        final String category = "sports";

        String url = UriComponentsBuilder
        .fromUriString(Url)
        .queryParam("apikey", newsApiKey)
        .queryParam("q", keyword)
        .queryParam("category", category)
        .queryParam("language", "en")
        .queryParam("page", page)
        .toUriString();


        RequestEntity req = RequestEntity.get(url).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        try {
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return collateNews(resp);
        
    }

    public List<News> collateNews(ResponseEntity<String> resp) throws IOException{
        JsonObject data = null;

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            data = reader.readObject();
        }

        JsonArray dataArr = data.getJsonArray("results");

        // System.out.println(">>>>dataArr: " + dataArr);
        
        News news = new News();

        for(int i=0; i <dataArr.size();i++){
            JsonObject obj = dataArr.getJsonObject(i);
           
            ValueType type = obj.get("image_url").getValueType();
            if(type != ValueType.NULL){
                
            // System.out.println(">>>>img: " + obj.get("image_url").toString());
                news = News.create(obj);
                if(newsList.size() < 9){
                    newsList.add(news);
                }
            }
            
        }

        if(newsList.size()<9){
            getNews(pageNumber + 1);
        }
        
        return newsList;
    }
}
