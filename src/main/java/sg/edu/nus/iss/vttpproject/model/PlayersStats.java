package sg.edu.nus.iss.vttpproject.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonObject;

public class PlayersStats {

    private Integer assists;
    private Integer blocks;
    private Integer steals;
    private Integer turnover;
    private Integer offensiveRebound;
    private Integer defensiveRebound;
    private Integer totalRebound;
    private Integer points;
    private Integer gamesPlay;
    private Integer pFouls;

    

    public Integer getAssists() {
        return assists;
    }



    public void setAssists(Integer assists) {
        this.assists = assists;
    }



    public Integer getBlocks() {
        return blocks;
    }



    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }



    public Integer getSteals() {
        return steals;
    }



    public void setSteals(Integer steals) {
        this.steals = steals;
    }



    public Integer getTurnover() {
        return turnover;
    }



    public void setTurnover(Integer turnover) {
        this.turnover = turnover;
    }



    public Integer getOffensiveRebound() {
        return offensiveRebound;
    }



    public void setOffensiveRebound(Integer offensiveRebound) {
        this.offensiveRebound = offensiveRebound;
    }



    public Integer getDefensiveRebound() {
        return defensiveRebound;
    }



    public void setDefensiveRebound(Integer defensiveRebound) {
        this.defensiveRebound = defensiveRebound;
    }



    public Integer getTotalRebound() {
        return totalRebound;
    }



    public void setTotalRebound(Integer totalRebound) {
        this.totalRebound = totalRebound;
    }



    public Integer getPoints() {
        return points;
    }



    public void setPoints(Integer points) {
        this.points = points;
    }



    public Integer getGamesPlay() {
        return gamesPlay;
    }



    public void setGamesPlay(Integer gamesPlay) {
        this.gamesPlay = gamesPlay;
    }



    public Integer getpFouls() {
        return pFouls;
    }



    public void setpFouls(Integer pFouls) {
        this.pFouls = pFouls;
    }



    public static PlayersStats create(JsonObject dataObj) {
        PlayersStats stats = new PlayersStats();
        try{
            stats.setAssists(Integer.parseInt(dataObj.getString("assists")));
        }catch(Exception e){
            stats.setAssists(null);
        }
        try{
            stats.setBlocks(Integer.parseInt(dataObj.getString("blocks")));
        }catch(Exception e){
            stats.setBlocks(null);
        }
        try{
            stats.setSteals(Integer.parseInt(dataObj.getString("steals")));
        }catch(Exception e){
            stats.setSteals(null);
        }
        try{
            stats.setTurnover(Integer.parseInt(dataObj.getString("turnovers")));
        }catch(Exception e){
            stats.setTurnover(null);
        }
        try{
            stats.setOffensiveRebound(Integer.parseInt(dataObj.getString("offReb")));
        }catch(Exception e){
            stats.setOffensiveRebound(null);
        }
        try{
            stats.setDefensiveRebound(Integer.parseInt(dataObj.getString("defReb")));
        }catch(Exception e){
            stats.setDefensiveRebound(null);
        }
        try{
            stats.setTotalRebound(Integer.parseInt(dataObj.getString("totReb")));
        }catch(Exception e){
            stats.setTotalRebound(null);
        }
        try{
            stats.setpFouls(Integer.parseInt(dataObj.getString("pFouls")));
        }catch(Exception e){
            stats.setpFouls(null);
        }
        try{
            stats.setPoints(Integer.parseInt(dataObj.getString("points")));
        }catch(Exception e){
            stats.setPoints(null);
        }
        try{
            stats.setGamesPlay(Integer.parseInt(dataObj.getString("gamesPlayed")));
        }catch(Exception e){
            stats.setGamesPlay(null);
        }
        return stats;
    }



    public PlayersStats convert(SqlRowSet rs) {
        PlayersStats stats = new PlayersStats();
        stats.setAssists(rs.getInt("assists"));
        stats.setBlocks(rs.getInt("blocks"));
        stats.setSteals(rs.getInt("steals"));
        stats.setTurnover(rs.getInt("turnovers"));
        stats.setOffensiveRebound(rs.getInt("offensiveRebound"));
        stats.setDefensiveRebound(rs.getInt("defensiveRebound"));
        stats.setTotalRebound(rs.getInt("totalRebound"));
        stats.setpFouls(rs.getInt("pFouls"));
        stats.setPoints(rs.getInt("points"));
        stats.setGamesPlay(rs.getInt("gamesPlay"));
        return stats;
    }
    
}
