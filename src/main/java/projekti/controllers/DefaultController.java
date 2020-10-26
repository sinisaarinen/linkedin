package projekti.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            System.out.println("username " + username);
            User user = userService.findByUsername(username);
            System.out.println("User++ " + user);
            model.addAttribute("message", user.getFullname());
        } else {
            model.addAttribute("message", "Anonymous!");
        }
        return "index";
    }
    
    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error");
        }
        System.out.println(user);
        userService.signUp(user);
        return "index";
    }
}