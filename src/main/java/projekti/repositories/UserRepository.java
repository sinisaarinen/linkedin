/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entitles.User;

/**
 *
 * @author saasini
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByProfilename(String profilename);
   //User findByName (String name); 
}
