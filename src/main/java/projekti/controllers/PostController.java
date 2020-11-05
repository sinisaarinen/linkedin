/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.entitles.*;
import projekti.repositories.PostRepository;
import projekti.services.CommentService;
import projekti.services.PostService;
import projekti.services.UserService;

/**
 *
 * @author saasini
 */

class PostObject {
    Post post;
    List<Like> likes;
    List<Comment> comments;
    boolean isCurrentUser;

    public PostObject() {
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setIsCurrentUser() {
        this.isCurrentUser = true;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public Post getPost() {
        return this.post;
    }

    public boolean getIsCurrentUser() {
        return this.isCurrentUser;
    }

    public Integer getLikeCount() {
        return this.likes.size();
    }


    public List<Comment> getComments() {
        return this.comments;
    }

}

@Controller
public class PostController {
    
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;
    
    @RequestMapping("/feed")
    public String feed(Model model) {
        User user = userService.currentUser();

        model.addAttribute("userId", user.getId());
        
        Page<Post> posts = postService.getAllPosts();
        List<PostObject> postObjects = new ArrayList<>();

        for (Post post : posts) {
            PostObject postObject = new PostObject();
            postObject.setPost(post);

            if (post.getPoster().getId() == user.getId()) {
                postObject.setIsCurrentUser();
            }

            List<Like> likes = postService.getLikesByPost(post.getId());
            postObject.setLikes(likes);
            
            ArrayList<Comment> comments = commentService.getCommentsByPost(post.getId());
            Collections.sort(comments);
            if (comments.size() > 10) {
                List<Comment> sublist = comments.subList(0, 10);
                postObject.setComments(sublist);
            } else {
                postObject.setComments(comments);
            }

            postObjects.add(postObject);
        }

        model.addAttribute("postObjects", postObjects);


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

    @PostMapping("/feed/comment/add/{id}")
    public String addComment(@PathVariable Long id, @RequestParam String content) {
        User user = userService.currentUser();
        commentService.addComment(user, content, id);
        return "redirect:/feed";
    }

    @RequestMapping(value="/feed/comment/{id}", method = RequestMethod.DELETE)
    public String deleteComment(@PathVariable Long id) {
        User user = userService.currentUser();
        commentService.deleteComment(user, id);
        return "redirect:/feed";
    }
    
    @PostMapping("/feed/like/{id}")
    public String likePost(@PathVariable Long id) {
        User user = userService.currentUser();
        postService.addOrDeleteLike(id, user);
        return "redirect:/feed";
    }
}
