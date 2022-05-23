package sg.edu.nus.iss.vttpproject.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.vttpproject.model.News;
import sg.edu.nus.iss.vttpproject.services.HomeService;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private HomeService hSvc;

    @GetMapping
    public String home(Model model, HttpSession sess) throws IOException{
        List<News> newsList = hSvc.getNews(0, null);
        model.addAttribute("newsList", newsList);
        String username =(String)sess.getAttribute("username");
        model.addAttribute("username", username);
        return "index";
    }

    @GetMapping("/searchnews")
    public String searchnews(@RequestParam String search, Model model, HttpSession sess) throws IOException{
        // String addkeyword = search + " AND " + keyword;
        boolean active = true;
        List<News> newsList = hSvc.getNews(0, search);
        model.addAttribute("newsList", newsList);
        String username =(String)sess.getAttribute("username");
        model.addAttribute("username", username);
        return "index";
    }
    
}
