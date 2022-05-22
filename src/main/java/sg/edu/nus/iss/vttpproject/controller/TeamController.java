package sg.edu.nus.iss.vttpproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.vttpproject.model.TeamStats;
import sg.edu.nus.iss.vttpproject.model.Teams;
import sg.edu.nus.iss.vttpproject.repository.TeamRepository;
import sg.edu.nus.iss.vttpproject.services.TeamService;
import sg.edu.nus.iss.vttpproject.services.UserService;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService tSvc;

    @Autowired
    private UserService uSvc;

    @GetMapping
    public String getTeams(Model model, HttpSession sess) throws IOException{
        List<Teams> teamsList = tSvc.getTeams();
        model.addAttribute("teamsList", teamsList);
        List<String> eastleagues = new ArrayList<>();
        eastleagues.add("EastSoutheast");
        eastleagues.add("EastAtlantic");
        eastleagues.add("EastCentral");
        model.addAttribute("eastleagues", eastleagues);
        List<String> westleagues = new ArrayList<>();
        westleagues.add("WestSouthwest");
        westleagues.add("WestNorthwest");
        westleagues.add("WestPacific");
        model.addAttribute("westleagues", westleagues);

        String username =(String)sess.getAttribute("username");
        model.addAttribute("username", username);

        //add data for first time
        // tSvc.addTeams(teamsList);
        return "teams";
    }

    @GetMapping("/stats/{id}")
    public String getStats(@PathVariable Integer id, Model model, HttpSession sess) throws IOException {
        // System.out.println(">>>>>>name: " + id);
        // check for username
        String username = (String) sess.getAttribute("username");
        model.addAttribute("username", username);
        // getstats
        TeamStats stats = tSvc.getStats(id);
        Teams team = tSvc.getTeam(id);
        model.addAttribute("team", team);

        // checkfav for add icon
        if (username != null) {
            if (uSvc.checkFavTeam(id, username)) {
                System.out.println(">>>>>>>id & username: " + id + " " + username);
                model.addAttribute("star", "fas");
                model.addAttribute("addedmessage", "Added to favourites!");
            } else {
                model.addAttribute("star", "far");
                model.addAttribute("addedmessage", "Add to favourites");
            }
            return "teamstats";
        }
        model.addAttribute("star", "far");
        model.addAttribute("addedmessage", "Add to favourites");
        return "teamstats";
    }

    @GetMapping("/addtofav/{id}")
    public String addToFav(@PathVariable Integer id, Model model, HttpSession sess) throws IOException {
        // get username
        String username = (String) sess.getAttribute("username");
        model.addAttribute("username", username);

        // get playerstats to reload page
        TeamStats stats = tSvc.getStats(id);
        Teams team = tSvc.getTeam(id);
        model.addAttribute("team", team);

        // action for username not null
        if (username != null) {
            boolean addOrDelete = uSvc.addToFavTeams(id, username);
            if (addOrDelete) {
                model.addAttribute("star", "fas");
                model.addAttribute("addedmessage", "Added to favourites!");
            } else if (!addOrDelete) {
                model.addAttribute("star", "far");
                model.addAttribute("addedmessage", "Add to favourites");
            }
            return "teamstats";
        }

        // prompt login if username null
        sess.setAttribute("teamid", id);
        return "login";
    }
    
}
