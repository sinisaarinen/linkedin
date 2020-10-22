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
    
    @ModelAttribute
    private User getUser() {
        return new User();
    }

    @GetMapping("*")
    public String frontPage(Model model) {
        model.addAttribute("message", "World!");
        return "index";
    }
    
    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error");
        }
        userService.signUp(user);
        return "index";
    }
}