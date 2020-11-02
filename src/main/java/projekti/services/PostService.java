/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entitles.Like;
import projekti.entitles.Post;
import projekti.entitles.User;
import projekti.repositories.LikeRepository;
import projekti.repositories.PostRepository;
import projekti.repositories.UserRepository;
/**
 *
 * @author saasini
 */
@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private LikeRepository likeRepository;
    
    public boolean addNewPost(User user, String content) {
        Date date = new Date(System.currentTimeMillis());
        Post post = new Post(content, date, user, new ArrayList<>(), new ArrayList<>());
        postRepository.save(post);
        return true;
    }
    
    public boolean deletePost(Post post) {
        postRepository.delete(post);
        return true;
    }
    
    public boolean likePost(Long postId, User liker) {
        Optional<Post> post = postRepository.findById(postId);
        Like like = new Like(liker, post);
        likeRepository.save(like);
        return true;
    }
    
    public boolean deleteLike(Like like) {
        likeRepository.delete(like);
        return true;
    }
    
    public boolean deletePost(Long id) {
        postRepository.deleteById(id);
        return true;
    }
    
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
