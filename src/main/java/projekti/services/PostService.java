/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    
    public boolean addOrDeleteLike(Long postId, User liker) {
        Optional<Post> possiblePost = postRepository.findById(postId);

        if (possiblePost.isPresent()) {
            Post post = possiblePost.get();
            if (likeRepository.findByPostAndLiker(post, liker) == null) {
                Like like = new Like(liker, post);
                likeRepository.save(like);
            } else {
                Like like = likeRepository.findByPostAndLiker(post, liker);
                likeRepository.delete(like);
            }

            List<Like> likesByPost = this.getLikesByPost(post.getId());
            System.out.println("LIKES BY POST " + likesByPost);
            List<Like> likesByLiker = this.getLikesByLiker(liker.getId());
            System.out.println("LIKES BY LIKER " + likesByLiker);

            System.out.println("COMMON: " + likesByPost.retainAll(likesByLiker));
            
            return true;
        }

        return false;
    }
    
    public boolean deleteLike(Like like) {
        likeRepository.delete(like);
        return true;
    }

    public List<Like> getLikesByPost(Long id) {
        return likeRepository.findByPostId(id);
    }

    public Post getPostById(Long id) {
        Optional<Post> possiblePost = postRepository.findById(id);
        if (possiblePost.isPresent()) {
            Post post = possiblePost.get();
            return post;
        }

        return null;
    }

    public List<Like> getLikesByLiker(Long id) {
        return likeRepository.findByLikerId(id);
    }
    
    public boolean deletePost(Long id) {
        postRepository.deleteById(id);
        return true;
    }
    
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
