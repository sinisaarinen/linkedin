/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entitles.Like;

import java.util.List;

/**
 *
 * @author saasini
 */
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostId(Long id);
    Like findOneByPostId(Long id);

    List<Like> findByLikerId(Long id);
    Like findOneByLikerId(Long id);
}
