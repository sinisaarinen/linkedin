/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entitles;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
public class Comment extends AbstractPersistable<Long> {
    
    @NotEmpty
    private String content;
    
    @OneToOne
    private User commentor;
    
}
