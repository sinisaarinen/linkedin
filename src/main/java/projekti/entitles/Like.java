/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entitles;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author saasini
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="Likes")
public class Like extends AbstractPersistable<Long> {
    
    @ManyToOne
    private User liker;
    
    @ManyToOne
    private Post post;
}
