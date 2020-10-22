/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entitles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Post extends AbstractPersistable<Long> implements Comparable<Post> {
    
    @NotEmpty
    private String content;
    
    private Date date;
    
    @ManyToOne
    private User poster;
    
    @ElementCollection(targetClass = String.class)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    
    public void removeComment() {
        Collections.sort(comments);
        comments.remove(comments.size() - 1);
    }
    
    @Override
    public int compareTo(Post post) {
        if (this.date.after(post.date)) {
            return -1;
        } else {
            return 1;
        }
    }
}
