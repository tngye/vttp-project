package sg.edu.nus.iss.vttpproject.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue.ValueType;

public class Players {
    private Integer id;
    private String name;
    private Date dob;
    private String country;
    private Integer startYear;
    private Float height;
    private Float weight;
    private String college;
    private Integer jerseyNo;
    private String strdob;
    private PlayersStats stats;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Integer getJerseyNo() {
        return jerseyNo;
    }

    public void setJerseyNo(Integer jerseyNo) {
        this.jerseyNo = jerseyNo;
    }

    public String getStrdob() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String strdob = null;
        if (dob != null) {
            strdob = sdf.format(dob);
        }
        return strdob;
    }

    public PlayersStats getStats() {
        return stats;
    }

    public void setStats(PlayersStats stats) {
        this.stats = stats;
    }

    public static Players create(JsonObject obj, PlayersStats stats) {

        Players player = new Players();
        player.setId(Integer.parseInt(obj.getString("personId")));
        player.setName(obj.getString("firstName") + " " + obj.getString("lastName"));
        player.setCountry(obj.getString("country"));
        player.setCollege(obj.getString("collegeName"));
        if (stats != null) {
            player.setStats(stats);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dob = format.parse(obj.getString("dateOfBirthUTC"));
            player.setDob(dob);
        } catch (Exception ex) {
            player.setDob(null);
        }

        try {
            player.setJerseyNo(Integer.parseInt(obj.getString("jersey")));
        } catch (Exception e) {
            player.setJerseyNo(null);
        }

        try {
            player.setStartYear(Integer.parseInt(obj.getString("nbaDebutYear")));
        } catch (Exception e) {
            player.setStartYear(null);
        }
        try {
            player.setHeight(Float.parseFloat(obj.getString("heightMeters")));
        } catch (Exception e) {
            player.setHeight(0f);
        }
        try {
            player.setWeight(Float.parseFloat(obj.getString("weightKilograms")));
        } catch (Exception e) {
            player.setWeight(0f);
        }
        return player;
    }

    public static Players create(JsonObject obj) {

        Players player = new Players();
        player.setId(Integer.parseInt(obj.getString("personId")));
        player.setName(obj.getString("firstName") + " " + obj.getString("lastName"));
        player.setCountry(obj.getString("country"));
        player.setCollege(obj.getString("collegeName"));
      
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dob = format.parse(obj.getString("dateOfBirthUTC"));
            player.setDob(dob);
        } catch (Exception ex) {
            player.setDob(null);
        }

        try {
            player.setJerseyNo(Integer.parseInt(obj.getString("jersey")));
        } catch (Exception e) {
            player.setJerseyNo(null);
        }

        try {
            player.setStartYear(Integer.parseInt(obj.getString("nbaDebutYear")));
        } catch (Exception e) {
            player.setStartYear(null);
        }
        try {
            player.setHeight(Float.parseFloat(obj.getString("heightMeters")));
        } catch (Exception e) {
            player.setHeight(0f);
        }
        try {
            player.setWeight(Float.parseFloat(obj.getString("weightKilograms")));
        } catch (Exception e) {
            player.setWeight(0f);
        }
        return player;
    }

    public static Players convert(SqlRowSet rs) {
        Players player = new Players();
        player.setId(rs.getInt("player_id"));
        player.setName(rs.getString("name"));
        player.setDob(rs.getDate("dob"));
        player.setCountry(rs.getString("country"));
        player.setStartYear(rs.getInt("startYear"));
        player.setHeight(rs.getFloat("height"));
        player.setWeight(rs.getFloat("weight"));
        player.setCollege(rs.getString("college"));
        player.setJerseyNo(rs.getInt("jerseyNo"));
        PlayersStats stats = new PlayersStats();
        stats = stats.convert(rs);
        player.setStats(stats);

        return player;
    }

    // public static Players create(JsonObject obj) {
    // Players player = new Players();
    // player.setId(obj.getInt("id"));
    // player.setName(obj.getString("firstname") + " " + obj.getString("lastname"));

    // player.setCountry(obj.getJsonObject("birth").get("country").getValueType() !=
    // ValueType.NULL
    // ? obj.getJsonObject("birth").getString("country")
    // : "");
    // player.setStartYear(obj.getJsonObject("nba").getInt("start"));
    // player.setHeight(obj.getJsonObject("height").get("meters").getValueType() !=
    // ValueType.NULL
    // ? Float.parseFloat(obj.getJsonObject("height").getString("meters"))
    // : 0f);
    // player.setWeight(obj.getJsonObject("weight").get("kilograms").getValueType()
    // != ValueType.NULL
    // ? Float.parseFloat(obj.getJsonObject("weight").getString("kilograms"))
    // : 0f);
    // player.setCollege(obj.get("college").getValueType() != ValueType.NULL ?
    // obj.getString("college") : "");

    // if(obj.containsKey("standard")){
    // player.setJerseyNo(obj.getJsonObject("leagues").getJsonObject("standard").getInt("jersey"));
    // }else if(obj.containsKey("vegas")){
    // player.setJerseyNo(obj.getJsonObject("leagues").getJsonObject("vegas").getInt("jersey"));
    // }
    // else{
    // player.setJerseyNo(null);
    // }

    // // player.setJerseyNo(obj.containsKey("standard") ?
    // obj.getJsonObject("leagues").getJsonObject("standard").getInt("jersey"):
    // obj.getJsonObject("leagues").getJsonObject("vegas").getInt("jersey"): null);
    // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    // if (obj.getJsonObject("birth").get("date").getValueType() != ValueType.NULL)
    // {
    // try {
    // Date dob = format.parse(obj.getJsonObject("birth").getString("date"));
    // player.setDob(dob);
    // } catch (Exception ex) {
    // ex.printStackTrace();
    // }
    // }
    // return player;
    // }

}
