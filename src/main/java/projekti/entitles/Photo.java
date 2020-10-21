/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entitles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class Photo extends AbstractPersistable<Long> {
    
    @OneToOne
    private User owner;
    
    private boolean setPP = false;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ElementCollection(targetClass = String.class)
    private List<String> likes = new ArrayList<>();
    
    public void removeComment() {
        Collections.sort(comments);
        comments.remove(comments.size() - 1);
    }
}
