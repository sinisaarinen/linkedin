/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entitles.Endorsement;
import projekti.entitles.Skill;
import projekti.entitles.User;

import java.util.List;

/**
 *
 * @author saasini
 */
public interface EndorsementRepository extends JpaRepository<Endorsement, Long>{
    
    List<Endorsement> findOneBySkillId(Long id);
    List<Endorsement> findBySkill(Skill skill);
    Endorsement findBySkillAndEndorser(Skill skill, User endorser);
}
