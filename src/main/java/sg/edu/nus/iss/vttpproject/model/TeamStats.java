package sg.edu.nus.iss.vttpproject.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonObject;

public class TeamStats {
    private Integer team_id;
    private Integer gamesPlayed;
    private Integer biggestLead;
    private Integer secondChancePoints;
    private Integer pointsOffTurnovers;
    private Integer longestRun;
    private Integer points;
    private Integer offReb;
    private Integer defReb;
    private Integer totReb;
    private Integer assists;
    private Integer pFouls;
    private Integer steals;
    private Integer turnovers;
    private Integer blocks;
    
    public Integer getTeam_id() {
        return team_id;
    }
    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }
    public Integer getGamesPlayed() {
        return gamesPlayed;
    }
    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
    public Integer getBiggestLead() {
        return biggestLead;
    }
    public void setBiggestLead(Integer biggestLead) {
        this.biggestLead = biggestLead;
    }
    public Integer getSecondChancePoints() {
        return secondChancePoints;
    }
    public void setSecondChancePoints(Integer secondChancePoints) {
        this.secondChancePoints = secondChancePoints;
    }
    public Integer getPointsOffTurnovers() {
        return pointsOffTurnovers;
    }
    public void setPointsOffTurnovers(Integer pointsOffTurnovers) {
        this.pointsOffTurnovers = pointsOffTurnovers;
    }
    public Integer getLongestRun() {
        return longestRun;
    }
    public void setLongestRun(Integer longestRun) {
        longestRun = longestRun;
    }
    public Integer getPoints() {
        return points;
    }
    public void setPoints(Integer points) {
        this.points = points;
    }
    public Integer getOffReb() {
        return offReb;
    }
    public void setOffReb(Integer offReb) {
        this.offReb = offReb;
    }
    public Integer getDefReb() {
        return defReb;
    }
    public void setDefReb(Integer defReb) {
        this.defReb = defReb;
    }
    public Integer getTotReb() {
        return totReb;
    }
    public void setTotReb(Integer totReb) {
        this.totReb = totReb;
    }
    public Integer getAssists() {
        return assists;
    }
    public void setAssists(Integer assists) {
        this.assists = assists;
    }
    public Integer getpFouls() {
        return pFouls;
    }
    public void setpFouls(Integer pFouls) {
        this.pFouls = pFouls;
    }
    public Integer getSteals() {
        return steals;
    }
    public void setSteals(Integer steals) {
        this.steals = steals;
    }
    public Integer getTurnovers() {
        return turnovers;
    }
    public void setTurnovers(Integer turnovers) {
        this.turnovers = turnovers;
    }
    public Integer getBlocks() {
        return blocks;
    }
    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public static TeamStats create(JsonObject dataObj) {
        TeamStats stats = new TeamStats();
        try{
            stats.setGamesPlayed(Integer.parseInt(dataObj.getString("gamesPlayed")));
        }catch(Exception e){
            stats.setGamesPlayed(null);
        }
        try{
            stats.setBiggestLead(Integer.parseInt(dataObj.getString("biggestLead")));
        }catch(Exception e){
            stats.setBiggestLead(null);
        }
        try{
            stats.setSecondChancePoints(Integer.parseInt(dataObj.getString("secondChancePoints")));
        }catch(Exception e){
            stats.setSecondChancePoints(null);
        }
        try{
            stats.setPointsOffTurnovers(Integer.parseInt(dataObj.getString("pointsOffTurnovers")));
        }catch(Exception e){
            stats.setPointsOffTurnovers(null);
        }
        try{
            stats.setLongestRun(Integer.parseInt(dataObj.getString("longestRun")));
        }catch(Exception e){
            stats.setLongestRun(null);
        }
        try{
            stats.setPoints(Integer.parseInt(dataObj.getString("points")));
        }catch(Exception e){
            stats.setPoints(null);
        }
        try{
            stats.setOffReb(Integer.parseInt(dataObj.getString("offReb")));
        }catch(Exception e){
            stats.setOffReb(null);
        }
        try{
            stats.setDefReb(Integer.parseInt(dataObj.getString("defReb")));
        }catch(Exception e){
            stats.setDefReb(null);
        }
        try{
            stats.setTotReb(Integer.parseInt(dataObj.getString("totReb")));
        }catch(Exception e){
            stats.setTotReb(null);
        }
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
            stats.setTurnovers(Integer.parseInt(dataObj.getString("turnovers")));
        }catch(Exception e){
            stats.setTurnovers(null);
        }
        
        try{
            stats.setpFouls(Integer.parseInt(dataObj.getString("pFouls")));
        }catch(Exception e){
            stats.setpFouls(null);
        }
       
        return stats;
    }
    public TeamStats convert(SqlRowSet rs) {
        TeamStats stats = new TeamStats();
        stats.setGamesPlayed(rs.getInt("gamesPlayed"));
        stats.setBiggestLead(rs.getInt("biggestLead"));
        stats.setSecondChancePoints(rs.getInt("secondChancePoints"));
        stats.setLongestRun(rs.getInt("longestRun"));
        stats.setPoints(rs.getInt("points"));
        stats.setOffReb(rs.getInt("offReb"));
        stats.setDefReb(rs.getInt("defReb"));
        stats.setTotReb(rs.getInt("totReb"));
        stats.setAssists(rs.getInt("assists"));
        stats.setpFouls(rs.getInt("pFouls"));
        stats.setBlocks(rs.getInt("blocks"));
        stats.setSteals(rs.getInt("steals"));
        stats.setTurnovers(rs.getInt("turnovers"));
        return stats;
    }
}
