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
import projekti.entitles.Comment;
import projekti.entitles.Post;
import projekti.entitles.User;
import projekti.repositories.CommentRepository;

/**
 *
 * @author saasini
 */
@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    public ArrayList<Comment> getCommentsByPost(Long id) {
        return new ArrayList<>(commentRepository.findByPostId(id));
    }

    public boolean addComment(User commentor, String content, Long id) {
        Date date = new Date(System.currentTimeMillis());
        Post post = postService.getPostById(id);
        Comment comment = new Comment(content, commentor, post, date);
        commentRepository.save(comment);
        return true;
    }
    
    public boolean deleteComment(User user, Long id) {
        Optional<Comment> possibleComment = commentRepository.findById(id);

        if (possibleComment.isPresent()) {
            Comment comment = possibleComment.get();
            if (comment.getCreator().getId() == user.getId()) {
                commentRepository.deleteById(id);
                return true;
            }
        }

        return false;
    }

}
