package sg.edu.nus.iss.vttpproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.vttpproject.model.Players;
import sg.edu.nus.iss.vttpproject.model.PlayersStats;
import sg.edu.nus.iss.vttpproject.model.Teams;
import sg.edu.nus.iss.vttpproject.services.PlayerService;
import sg.edu.nus.iss.vttpproject.services.TeamService;
import sg.edu.nus.iss.vttpproject.services.UserService;

@Controller
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private TeamService tSvc;

    @Autowired
    private PlayerService pSvc;

    @Autowired
    private UserService uSvc;

    // @GetMapping
    // public String getPlayers(Model model) throws IOException{
    // List<Teams> teamsList = tSvc.getTeamsFromDb();
    // List<Players> playerList = new ArrayList<>();
    // // for(Teams t: teamsList){
    // for(int i =0; i< 10; i++){
    // playerList.addAll(pSvc.getPlayers(teamsList.get(i).getId()));
    // }
    // model.addAttribute("playerList", playerList);
    // System.out.println(">>>>modelList" + playerList);
    // return "players";
    // }

    @GetMapping
    public String getPlayers(Model model, HttpSession sess) throws IOException {
        List<Players> playerList = new ArrayList<>();
        playerList.addAll(pSvc.getPlayers());
        model.addAttribute("playerList", playerList);
        String username = (String) sess.getAttribute("username");
        model.addAttribute("username", username);
        return "players";
    }

    @GetMapping("/stats/{id}")
    public String getStats(@PathVariable Integer id, Model model, HttpSession sess) throws IOException {
        // System.out.println(">>>>>>name: " + id);
        // check for username
        String username = (String) sess.getAttribute("username");
        model.addAttribute("username", username);
        // getstats
        PlayersStats stats = pSvc.getStats(id);
        Players player = pSvc.getPlayer(id);
        model.addAttribute("player", player);

        // checkfav for add icon
        if (username != null) {
            if (uSvc.checkFav(id, username)) {
                System.out.println(">>>>>>>id & username: " + id + " " + username);
                model.addAttribute("star", "fas");
                model.addAttribute("addedmessage", "Added to favourites!");
            } else {
                model.addAttribute("star", "far");
                model.addAttribute("addedmessage", "Add to favourites");
            }
            return "stats";
        }
        model.addAttribute("star", "far");
        model.addAttribute("addedmessage", "Add to favourites");
        return "stats";
    }

    @GetMapping("/addtofav/{id}")
    public String addToFav(@PathVariable Integer id, Model model, HttpSession sess) throws IOException {
        // get username
        String username = (String) sess.getAttribute("username");
        model.addAttribute("username", username);

        // get playerstats to reload page
        PlayersStats stats = pSvc.getStats(id);
        Players player = pSvc.getPlayer(id);
        model.addAttribute("player", player);

        // action for username not null
        if (username != null) {
            boolean addOrDelete = uSvc.addToFav(id, username);
            if (addOrDelete) {
                model.addAttribute("star", "fas");
                model.addAttribute("addedmessage", "Added to favourites!");
            } else if (!addOrDelete) {
                model.addAttribute("star", "far");
                model.addAttribute("addedmessage", "Add to favourites");
            }
            return "stats";
        }

        // prompt login if username null
        sess.setAttribute("id", id);
        return "login";
    }
}
