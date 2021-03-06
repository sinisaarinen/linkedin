/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entitles;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
/**
 *
 * @author saasini
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Endorsement extends AbstractPersistable<Long> {
    
    @ManyToOne
    private User endorser;
    
    @ManyToOne
    @JoinColumn(name="skill_id")
    private Skill skill;
}
