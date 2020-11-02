/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entitles.Post;
import projekti.entitles.Skill;

/**
 *
 * @author saasini
 */
public interface PostRepository extends JpaRepository<Post, Long> {
        Optional<Post> findById(Long id);

}
