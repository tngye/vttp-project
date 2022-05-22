package sg.edu.nus.iss.vttpproject.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
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
    // willberetainedafterreturn
    // public List<News> newsList2 = newsList;

    public Integer pageNumber;
    private String search;

    public List<News> getNews(Integer page, String search) throws IOException {
        
        System.out.println(">>>newsList.size: " + newsList.size());
        pageNumber = page;
        final String Url = "https://newsdata.io/api/1/news";
        final String keyword = "nba";
        final String category = "sports";
        String addedkeyword = keyword;

        if (search != null) {
            addedkeyword = search + " AND " + keyword;
        }

        System.out.println(">>>addedkeyword: " + addedkeyword);

        String url = UriComponentsBuilder
                .fromUriString(Url)
                .queryParam("apikey", newsApiKey)
                .queryParam("q", URLEncoder.encode(addedkeyword, "UTF-8"))
                .queryParam("category", category)
                .queryParam("language", "en")
                .queryParam("page", page)
                .toUriString();

        System.out.println(">>>url: " + url);

        RequestEntity req = RequestEntity.get(url).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        try {
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return collateNews(resp, search);

    }

    // public List<News> collateNews(ResponseEntity<String> resp) throws IOException
    // {
    // JsonObject data = null;

    // try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
    // JsonReader reader = Json.createReader(is);
    // data = reader.readObject();
    // }

    // JsonArray dataArr = data.getJsonArray("results");

    // News news = new News();

    // for (int i = 0; i < dataArr.size(); i++) {
    // JsonObject obj = dataArr.getJsonObject(i);

    // ValueType type = obj.get("image_url").getValueType();
    // if (type != ValueType.NULL) {

    // news = News.create(obj);
    // if (search == null) {
    // if (newsList.size() < 9) {
    // newsList.add(news);
    // }
    // }else{
    // if (searchNewsList.size() < 9) {
    // searchNewsList.add(news);
    // }
    // }
    // }

    // }
    // if (search == null) {
    // if (newsList.size() < 9) {
    // getNews(pageNumber + 1, search);
    // }

    // return newsList;
    // } else {
    // if (searchNewsList.size() < 9) {
    // getNews(pageNumber + 1, search);
    // }

    // return searchNewsList;
    // }
    // }
    // }

//     public List<News> collateNews(ResponseEntity<String> resp, String search) throws IOException {
//         JsonObject data = null;

//         try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
//             JsonReader reader = Json.createReader(is);
//             data = reader.readObject();
//         }

//         JsonArray dataArr = data.getJsonArray("results");

//         // System.out.println(">>>>dataArr: " + dataArr);

//         News news = new News();

//         for (int i = 0; i < dataArr.size(); i++) {
//             JsonObject obj = dataArr.getJsonObject(i);

//             ValueType type = obj.get("image_url").getValueType();
//             if (type != ValueType.NULL) {
//                 news = News.create(obj);
//                 if (newsList.size() == 9) {
//                     newsList.clear();
//                 }

//                 if (newsList.size() < 9) {
//                     newsList.add(news);
//                 }
//             }

//         }
//         int pagelimit = (data.getInt("totalResults") / 10) + 1;

//         if (newsList.size() < 9) {
//             getNews(pageNumber + 1, search);
//         }
//         this.search = search;

//         return newsList;
//     }
// }
public List<News> collateNews(ResponseEntity<String> resp, String search) throws IOException{
    JsonObject data = null;

    try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
        JsonReader reader = Json.createReader(is);
        data = reader.readObject();
    }

    JsonArray dataArr = data.getJsonArray("results");

    // System.out.println(">>>>dataArr: " + dataArr);
    
    News news = new News();

    System.out.print("search: " +search);
    System.out.print("thissearch: " +this.search);
    if(search != this.search){
        newsList.clear();
    }

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

    int pagelimit = (data.getInt("totalResults") / 10) + 1;
    if(newsList.size()<9 && pageNumber < pagelimit){
        this.search = search;
        getNews(pageNumber + 1, search);
    }
    
    return newsList;
}
}
