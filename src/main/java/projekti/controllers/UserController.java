/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import projekti.entitles.User;
import projekti.repositories.UserRepository;
import projekti.services.UserService;

/**
 *
 * @author saasini
 */
@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            System.out.println("username " + username);
            User user = userService.findByUsername(username);
            System.out.println("User++ " + user);
            model.addAttribute("fullname", user.getFullname());
        } else {
            model.addAttribute("message", "Anonymous!");
        }
        return "profile";
    }
    //@GetMapping("/users/{shortname}")
    //public String userProfile(Model model, @PathVariable String shortname) {
    
}
