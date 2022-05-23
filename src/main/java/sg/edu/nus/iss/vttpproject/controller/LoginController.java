package sg.edu.nus.iss.vttpproject.controller;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.vttpproject.model.Users;
import sg.edu.nus.iss.vttpproject.services.LoginService;

@Controller
@RequestMapping()
public class LoginController {

    @Autowired
    private LoginService lSvc;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession sess) {
        ModelAndView mvc = new ModelAndView();
        sess.invalidate();
        mvc = new ModelAndView("redirect:/");
        return mvc;
    }

    @PostMapping("/signup")
    public String signupPost(@RequestBody MultiValueMap<String, String> form, Model model, HttpSession sess) {
        try {
            boolean added = lSvc.addUser(form);
            model.addAttribute("message", "Sign up successfully!");
            // sess.setAttribute("message", "Sign up successfully!");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Username taken!");
            // sess.setAttribute("message", "Sign up successfully!");
            return "signup";
        }
    }

    @PostMapping("/authenticate")
    public ModelAndView login(@RequestBody MultiValueMap<String, String> form, HttpSession sess) {

        Users user = Users.convert(form);
        String username = user.getUsername();
        String password = user.getPassword();

        boolean authenticate = lSvc.authenticateUser(username, password);

        ModelAndView mvc = new ModelAndView();

        if (!authenticate) {
            // Not successful
            mvc.setViewName("login");
            mvc.addObject("error", "Login Invalid!");
            mvc.setStatus(HttpStatus.FORBIDDEN);

        } else {
            // Successful
            sess.setAttribute("username", username);
            mvc = new ModelAndView("redirect:/");

            Integer id = (Integer) sess.getAttribute("id");
            if (id != null) {
                mvc = new ModelAndView("redirect:/players/addtofav/" + id);
            }
            Integer teamid = (Integer) sess.getAttribute("teamid");
            if (teamid != null) {
                mvc = new ModelAndView("redirect:/teams/addtofav/" + teamid);
            }

        }

        return mvc;
    }

}
