/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entitles.Endorsement;

/**
 *
 * @author saasini
 */
public interface EndorsementRepository extends JpaRepository<Endorsement, Long>{
    
}