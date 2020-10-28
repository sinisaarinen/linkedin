/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.entitles.Skill;
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
            model.addAttribute("skills", user.getSkills());
            System.out.println(user.getSkills());
        } else {
            model.addAttribute("message", "Anonymous!");
        }
        return "profile";
    }
    
    @PostMapping("/addskill")
    public String addSkill(@RequestParam String skill) {
        System.out.println("Moi");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            userService.addSkill(skill, user);
        }
        System.out.println(skill);
        return "redirect:/profile";
    }
    
    //@GetMapping("/users/{shortname}")
    //public String userProfile(Model model, @PathVariable String shortname) {
    
}
