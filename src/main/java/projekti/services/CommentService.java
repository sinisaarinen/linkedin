/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.Date;
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
    
    public boolean addComment(User commentor, String content, Post post) {
        Date date = new Date(System.currentTimeMillis());
        Comment comment = new Comment(content, commentor, post, date);
        commentRepository.save(comment);
        return true;
    }
    
    public boolean deleteComment(Comment comment) {
        commentRepository.delete(comment);
        return true;
    }
}
