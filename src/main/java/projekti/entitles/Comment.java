/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entitles;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class Comment extends AbstractPersistable<Long> implements Comparable<Comment> {
    
    @NotEmpty
    private String content;
    
    @ManyToOne
    private User creator;
    
    private Date date;
    
    @Override
    public int compareTo(Comment comment) {
        if (this.date.after(comment.date)) {
            return -1;
        } else {
            return 1;
        }
    }
}
