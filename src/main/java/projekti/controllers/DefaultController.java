package projekti.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.entitles.User;
import projekti.services.UserService;

@Controller
public class DefaultController {
    
    @Autowired
    private UserService userService;

    @GetMapping("*")
    public String frontPage(Model model) {
        model.addAttribute("message", "World!");
        return "index";
    }
    
    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", user);
        return "/";
    }
}