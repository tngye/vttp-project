package sg.edu.nus.iss.vttpproject.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.vttpproject.model.News;
import sg.edu.nus.iss.vttpproject.services.HomeService;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private HomeService hSvc;

    @GetMapping
    public String home(Model model) throws IOException{
        List<News> newsList = hSvc.getNews(0);
        model.addAttribute("newsList", newsList);
        return "index";
    }
    
}
