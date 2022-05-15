package sg.edu.nus.iss.vttpproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.vttpproject.model.Teams;
import sg.edu.nus.iss.vttpproject.services.TeamService;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService tSvc;

    @GetMapping
    public String getTeams(Model model) throws IOException{
        List<Teams> teamsList = tSvc.getTeams();
        model.addAttribute("teamsList", teamsList);
        List<String> eastleagues = new ArrayList<>();
        eastleagues.add("EastAtlantic");
        eastleagues.add("EastCentral");
        model.addAttribute("eastleagues", eastleagues);
        List<String> westleagues = new ArrayList<>();
        westleagues.add("WestSouthwest");
        westleagues.add("WestNorthwest");
        westleagues.add("WestPacific");
        model.addAttribute("westleagues", westleagues);
        return "teams";
    }
    
}
