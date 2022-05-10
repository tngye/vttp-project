package sg.edu.nus.iss.vttpproject.model;

import jakarta.json.JsonObject;

public class News {
    private String title;
    private String link;
    private String imgUrl;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static News create(JsonObject obj) {
        News news = new News();
        news.setImgUrl(obj.getString("image_url")); 
        news.setLink(obj.getString("link"));
        news.setTitle(obj.getString("title"));

        return news;
    }

    
    
}
