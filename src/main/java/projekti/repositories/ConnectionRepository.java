/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entitles.Connection;
import projekti.entitles.User;

/**
 *
 * @author saasini
 */
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    
    List<Connection> findBySender (User sender);
    List<Connection> findByReceiver (User receiver);
    Connection findBySenderAndReceiver (User sender, User receiver);
}
