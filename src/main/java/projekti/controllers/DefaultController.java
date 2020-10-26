package projekti.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import projekti.entitles.User;
import projekti.repositories.UserRepository;
import projekti.services.UserService;

@Controller
public class DefaultController {
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @ModelAttribute
    private User getUser() {
        return new User();
    }

    @RequestMapping("/signup")
    public String signUp(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("message", user.getFullname());
        } else {
            model.addAttribute("message", "Anonymous!");
        }
        return "signup";
    }
    
    @GetMapping("*")
    public String frontPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
           return "redirect:/profile";
        } else {
           return "index";
        }
    }
    
    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        if (userService.findByUsername(user.getUsername()) != null ||
                userRepository.findByProfilename(user.getProfilename()) != null) {
            return "redirect:/signup";
        }
        userService.signUp(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getFullname(), user.getProfilename());
        return "redirect:/profile";
    }
    

}