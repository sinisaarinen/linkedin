/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.entitles.Endorsement;
import projekti.entitles.Post;
import projekti.entitles.Skill;
import projekti.entitles.User;
import projekti.repositories.PostRepository;
import projekti.services.PostService;
import projekti.services.UserService;

/**
 *
 * @author saasini
 */
@Controller
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("/feed")
    public String feed(Model model) {
        User user = userService.currentUser();

        model.addAttribute("userId", user.getId());

        List<Post> posts = postService.getAllPosts();
        
        if (true) {
            model.addAttribute("isCurrentUser", true);
        }
                
        model.addAttribute("posts", posts);


        return "feed";
    }
    
    @PostMapping("/feed/add")
    public String addPost(@RequestParam String content) {
        User user = userService.currentUser();
        postService.addNewPost(user, content);
        return "redirect:/feed";
    }

    @RequestMapping(value="/feed/{id}", method = RequestMethod.DELETE)
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/feed";
    }
    
    @PostMapping("/feed/add")
    public String likePost(@PathVariable Long id) {
        postService.likePostById(id);
        return "redirect:/feed";
    }
}
