/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entitles.Comment;
/**
 *
 * @author saasini
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
