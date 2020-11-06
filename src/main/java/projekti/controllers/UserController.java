/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import projekti.entitles.Endorsement;
import projekti.entitles.Skill;
import projekti.entitles.User;
import projekti.repositories.UserRepository;
import projekti.services.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author saasini
 */

class SkillCounter implements Comparable<SkillCounter> {
    private Skill skill;
    private Integer count;

    public SkillCounter(Skill skill, Integer count) {
        this.skill = skill;
        this.count = count;
    }

    public Integer getCount() {
        return this.count;
    }

    public Skill getSkill() {
        return this.skill;
    }
    
    @Override
    public int compareTo(SkillCounter skillCounter) {
        if (this.count < skillCounter.count) {
            return -1;
        } else {
            return 1;
        }
    }
}

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("/profile")
    public String profile() {
        User user = userService.currentUser();
        String username = user.getUsername();

        if (username != null) {
            return "redirect:/profile/" + username;
        } else {
            return "404";
        }
    }

    @RequestMapping("/profile/{username}")
    public String profileById(@PathVariable String username, Model model) {
        User user = userService.getByUsername(username);

        if (user.getId() == userService.currentUser().getId()) {
            model.addAttribute("isCurrentUser", true);
            System.out.println("isCurrent");
        }

        model.addAttribute("fullname", user.getFullname());
        model.addAttribute("userId", user.getId());

        ArrayList<SkillCounter> skillz = new ArrayList<>();

        for (Skill skill : userService.getSkillsById(user.getId())) {
            List<Endorsement> endorsements = userService.getEndorsementsBySkill(skill.getId());
            SkillCounter counter = new SkillCounter(skill, endorsements.size());
            skillz.add(counter);
        }
        Collections.sort(skillz);

        model.addAttribute("skills", skillz);
        model.addAttribute("user", user);


        return "profile";
    }

    @PostMapping("/profile/endorse")
    public String endorseSkill(@RequestParam String skillId, @RequestParam String username) {
        System.out.println("Skill " + Long.parseLong(skillId));
        User user = userService.currentUser();
        System.out.println("endorser " + user.getId());
        userService.addOrDeleteEndorsement(userService.currentUser(),  Long.parseLong(skillId));
        return "redirect:/profile/" + username;
    }
    
    @PostMapping("/profile")
    public String addSkill(@RequestParam String skill) {
        User user = userService.currentUser();
        userService.addSkill(skill, user);
        return "redirect:/profile";
    }

    @RequestMapping(value="/profile/{id}", method = RequestMethod.DELETE)
    public String deleteSkill(@PathVariable Long id) {
        userService.deleteSkill(id);
        return "redirect:/profile";
    }
    
    @PostMapping("/profile/{profilename}/addphoto")
    public String uploadPhoto(@RequestParam("photo") MultipartFile file) throws IOException {
        if (file != null) {
            userService.addPhoto(file);
        }
        return "redirect:/profile";
    }
    
    @GetMapping("/profile/{profilename}/photo")
    public ResponseEntity<byte[]> viewPhoto() {
        User user = userService.currentUser();
        byte[] photo = user.getPhoto();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + user.getUsername() + "-photo");
        headers.setContentType(MediaType.parseMediaType(user.getPhotoContentType()));
        return new ResponseEntity<>(photo, headers, HttpStatus.CREATED);
        }
}