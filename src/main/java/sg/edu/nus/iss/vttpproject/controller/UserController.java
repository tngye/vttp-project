package sg.edu.nus.iss.vttpproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.vttpproject.model.Players;
import sg.edu.nus.iss.vttpproject.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired 
    private UserService uSvc;
    
    @GetMapping("/{username}/favourites")
    public String getFavourites(@PathVariable String username, Model model){
        List<Players> fav = uSvc.getFavourites(username);
        model.addAttribute("playerList", fav);
        return "favourites";
    }
}
